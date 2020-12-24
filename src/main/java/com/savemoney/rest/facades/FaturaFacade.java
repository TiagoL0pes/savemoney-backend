package com.savemoney.rest.facades;

import com.savemoney.domain.enums.StatusPagamento;
import com.savemoney.domain.mappers.FaturaMapper;
import com.savemoney.domain.models.CartaoCredito;
import com.savemoney.domain.models.ContaBancaria;
import com.savemoney.domain.models.Fatura;
import com.savemoney.domain.models.Parcela;
import com.savemoney.domain.pagination.FaturasPagination;
import com.savemoney.domain.requests.FaturaRequest;
import com.savemoney.domain.responses.FaturaResponse;
import com.savemoney.rest.services.CartaoCreditoService;
import com.savemoney.rest.services.FaturaService;
import com.savemoney.rest.services.ParcelaService;
import com.savemoney.utils.exceptions.BadRequestException;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FaturaFacade {

    @Autowired
    private FaturaService faturaService;

    @Autowired
    private CartaoCreditoService cartaoCreditoService;

    @Autowired
    private ParcelaService parcelaService;

    private final FaturaMapper faturaMapper =
            Mappers.getMapper(FaturaMapper.class);

    public Fatura gerarFatura(FaturaRequest request) {
        CartaoCredito cartaoCredito = cartaoCreditoService.buscarPorId(request.getIdCartao());
        ContaBancaria contaBancaria = cartaoCredito.getContaBancaria();
        LocalDate dataVencimento;

        if (Objects.nonNull(request.getMes()) && Objects.nonNull(request.getAno())) {
            dataVencimento = LocalDate.of(request.getAno(), request.getMes(), cartaoCredito.getDiaVencimento());
        } else {
            dataVencimento = gerarProximaDataVencimento(cartaoCredito.getDiaVencimento());
        }

        verificaSeFaturaJaFoiGerada(dataVencimento);
        List<Parcela> parcelasParaGerarFatura =
                parcelaService.buscarParcelasParaGerarFatura(dataVencimento, cartaoCredito.getContaBancaria());

        validarParcelas(parcelasParaGerarFatura);
        Fatura fatura = new Fatura(parcelasParaGerarFatura, StatusPagamento.PENDENTE, dataVencimento);
        fatura.setCartaoCredito(cartaoCredito);
        fatura = faturaService.gerar(fatura);

        cartaoCredito.adicionarFatura(fatura);
        cartaoCreditoService.atualizar(cartaoCredito.getIdCartaoCredito(), cartaoCredito, contaBancaria);

        return fatura;
    }

    private LocalDate gerarProximaDataVencimento(Integer diaVencimento) {
        final int DIAS_ANTERIORES_FECHAMENTO = 10;
        LocalDate hoje = LocalDate.now();
        LocalDate dataFechamento = LocalDate.of(hoje.getYear(), hoje.getMonthValue(), diaVencimento);

        long diferencaDiasEntreHojeEFechamento =
                Duration.between(hoje.atStartOfDay(), dataFechamento.atStartOfDay()).toDays();
        diferencaDiasEntreHojeEFechamento = Math.abs(diferencaDiasEntreHojeEFechamento);
        if (diferencaDiasEntreHojeEFechamento <= DIAS_ANTERIORES_FECHAMENTO) {
            return dataFechamento.plusMonths(1);
        }
        return LocalDate.of(hoje.getYear(), hoje.getMonth(), diaVencimento);
    }

    private void verificaSeFaturaJaFoiGerada(LocalDate dataVencimento) {
        faturaService.buscarPorDataVencimento(dataVencimento);
    }

    public void atualizarFatura(Long idFatura, FaturaRequest request) {
        Fatura fatura = faturaService.buscarPorId(idFatura);
        if (StatusPagamento.PAGO == fatura.getStatusPagamento()) {
            throw new BadRequestException("Não é possível atualizar uma fatura paga");
        }

        CartaoCredito cartaoCredito = cartaoCreditoService.buscarPorId(request.getIdCartao());

        List<Parcela> parcelas =
                parcelaService.buscarParcelasParaGerarFatura(fatura.getDataVencimento(), cartaoCredito.getContaBancaria());
        validarParcelas(parcelas);

        List<Parcela> novasParcelas = buscarNovasParcelas(fatura, parcelas);
        if (CollectionUtils.isEmpty(novasParcelas)) {
            throw new BadRequestException("Não há itens novos para atualizar a fatura");
        }

        BigDecimal valorTotalAtualizado = atualizarValorTotal(novasParcelas);

        fatura.getParcelas().addAll(novasParcelas);
        fatura.setTotal(fatura.getTotal().add(valorTotalAtualizado));
        faturaService.atualizarStatus(fatura);
    }

    private void validarParcelas(List<Parcela> parcelas) {
        if (CollectionUtils.isEmpty(parcelas)) {
            throw new BadRequestException("Não há itens para gerar a fatura");
        }
    }

    private List<Parcela> buscarNovasParcelas(Fatura fatura, List<Parcela> parcelas) {
        return parcelas.stream()
                .filter(parcela -> !fatura.getParcelas().contains(parcela) &&
                        StatusPagamento.PENDENTE == parcela.getStatusPagamento())
                .collect(Collectors.toList());
    }

    private BigDecimal atualizarValorTotal(List<Parcela> parcelas) {
        return parcelas.stream()
                .map(Parcela::getValor)
                .reduce(BigDecimal::add)
                .get();
    }

    public FaturaResponse buscarPorId(Long idFatura) {
        Fatura fatura = faturaService.buscarPorId(idFatura);
        return faturaMapper.toFaturaResponse(fatura);
    }

    public FaturasPagination listar(Long idCartao, Pageable pageable) {
        cartaoCreditoService.buscarPorId(idCartao);
        return faturaService.listar(idCartao, pageable);
    }

    public void atualizarStatus(Fatura fatura) {
        faturaService.atualizarStatus(fatura);
    }
}
