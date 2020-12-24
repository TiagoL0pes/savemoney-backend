package com.savemoney.rest.facades;

import com.savemoney.domain.enums.StatusPagamento;
import com.savemoney.domain.mappers.DespesaMapper;
import com.savemoney.domain.mappers.FaturaMapper;
import com.savemoney.domain.models.*;
import com.savemoney.rest.commands.PagamentoCommand;
import com.savemoney.rest.factories.PagamentoCommandFactory;
import com.savemoney.rest.services.ContaBancariaService;
import com.savemoney.rest.services.DespesaService;
import com.savemoney.rest.services.FaturaService;
import com.savemoney.rest.services.ParcelaService;
import com.savemoney.utils.exceptions.ResourceNotFoundException;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class PagamentoFacade {

    @Autowired
    private DespesaService despesaService;

    @Autowired
    private ContaBancariaService contaBancariaService;

    @Autowired
    private ParcelaService parcelaService;

    @Autowired
    private FaturaService faturaService;

    @Autowired
    private PagamentoCommandFactory pagamentoCommandFactory;

    private final DespesaMapper despesaMapper =
            Mappers.getMapper(DespesaMapper.class);

    private final FaturaMapper faturaMapper =
            Mappers.getMapper(FaturaMapper.class);

    public void pagarDespesa(String token, Long id) {
        ContaBancaria conta = contaBancariaService.recuperarContaBancaria(token);
        Despesa despesa = despesaService.buscarPorId(id, conta);

        PagamentoCommand pagamentoCommand = pagamentoCommandFactory.getCommand(despesa.getStatusPagamento());
        pagamentoCommand.execute(despesa, conta);

        contaBancariaService.atualizarSaldo(conta);
        atualizarStatusDespesa(despesa);
    }

    private void atualizarStatusDespesa(Despesa despesa) {
        despesa.setDataPagamento(LocalDate.now());
        despesa.setStatusPagamento(StatusPagamento.PAGO);

        despesaService.atualizarStatus(despesa);
    }

    public void pagarFatura(String token, Long id) {
        ContaBancaria contaBancaria = contaBancariaService.recuperarContaBancaria(token);
        CartaoCredito cartaoCredito = buscarCartaoCredito(contaBancaria);
        Fatura fatura = faturaService.buscarPorId(id);
        List<Parcela> parcelas = fatura.getParcelas();

        Despesa despesa = despesaMapper.toDespesa(fatura);
        PagamentoCommand pagamentoCommand = pagamentoCommandFactory.getCommand(fatura.getStatusPagamento());
        pagamentoCommand.execute(despesa, contaBancaria);

        for (Parcela parcela : fatura.getParcelas()) {
            atualizarStatusParcela(parcela.getIdParcela(), cartaoCredito);
        }

        ajustarLimiteCartaoAposPagamentoParcela(contaBancaria, cartaoCredito, despesa.getValor());

        fatura = faturaMapper.toFatura(despesa);
        fatura.setParcelas(parcelas);
        fatura.setCartaoCredito(cartaoCredito);
        contaBancariaService.atualizarSaldo(contaBancaria);
        faturaService.atualizarStatus(fatura);
    }

    private CartaoCredito buscarCartaoCredito(ContaBancaria contaBancaria) {
        return contaBancaria.getCartoesCredito().stream()
                .filter(cartao -> cartao.getContaBancaria().equals(contaBancaria))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Cartão de crédito não encontrado"));
    }

    private void atualizarStatusParcela(Long id, CartaoCredito cartaoCredito) {
        Parcela parcela = buscarParcelaNoCartao(id, cartaoCredito);
        parcela.setDataPagamento(LocalDate.now());
        parcela.setStatusPagamento(StatusPagamento.PAGO);

        parcelaService.atualizarStatus(parcela);
    }

    private Parcela buscarParcelaNoCartao(Long idParcela, CartaoCredito cartaoCredito) {
        return cartaoCredito.getItens().stream()
                .map(ItemCartao::getParcelas)
                .flatMap(List::stream)
                .filter(p -> p.getIdParcela().equals(idParcela))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Parcela não encontrada"));
    }

    private void ajustarLimiteCartaoAposPagamentoParcela(ContaBancaria contaBancaria,
                                                         CartaoCredito cartaoCredito,
                                                         BigDecimal valor) {
        contaBancaria.getCartoesCredito().stream()
                .filter(cartao -> cartao.getNumero().equals(cartaoCredito.getNumero()))
                .findFirst()
                .map(cartao -> {
                    cartao.setLimiteUtilizado(cartao.getLimiteUtilizado().subtract(valor));
                    return cartao;
                });
    }
}
