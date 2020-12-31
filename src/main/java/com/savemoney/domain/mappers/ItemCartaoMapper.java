package com.savemoney.domain.mappers;

import com.savemoney.domain.enums.Mes;
import com.savemoney.domain.enums.StatusPagamento;
import com.savemoney.domain.models.ItemCartao;
import com.savemoney.domain.models.Parcela;
import com.savemoney.domain.requests.ItemCartaoRequest;
import com.savemoney.utils.helpers.DateHelper;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Mapper(imports = {DateHelper.class})
public interface ItemCartaoMapper extends DefaultMapper {

    @Mapping(target = "dataCompra", expression = "java(DateHelper.toLocalDate(request.getDataCompra()))")
    @Mapping(target = "descricao", source = "request.descricao")
    @Mapping(target = "valorTotal", source = "request.valorTotal")
    @Mapping(target = "numeroParcelas", source = "request.numeroParcelas")
    ItemCartao toItemCartao(ItemCartaoRequest request, Integer diaVencimento);

    @AfterMapping
    default void toItemCartao(@MappingTarget ItemCartao itemCartao, Integer diaVencimento) {
        LocalDate dataVencimento = LocalDate.of(
                itemCartao.getDataCompra().getYear(),
                itemCartao.getDataCompra().getMonth(),
                diaVencimento);

        for (int indice = 1; indice <= itemCartao.getNumeroParcelas(); indice++) {
            dataVencimento = ajustarDataVencimento(dataVencimento);
            Parcela parcela = gerarParcela(itemCartao, indice, dataVencimento);
            itemCartao.adicionarParcela(parcela);
        }

        aplicarDesconto(itemCartao);
    }

    @Named("ajustarDataVencimento")
    default LocalDate ajustarDataVencimento(LocalDate dataVencimento) {
        return dataVencimento.plusMonths(1);
    }

    default Parcela gerarParcela(ItemCartao itemCartao, Integer indice, LocalDate dataVencimento) {
        Mes mes = Mes.getNumeroMes(dataVencimento.getMonthValue());

        Parcela parcela = new Parcela(
                null,
                indice,
                itemCartao.getValorParcela(),
                StatusPagamento.PENDENTE,
                dataVencimento);

        parcela.setDescricao(itemCartao.getDescricao() + " - " + indice + "/" + itemCartao.getNumeroParcelas());
        parcela.setMesVencimento(mes);

        return parcela;
    }

    default void aplicarDesconto(ItemCartao itemCartao) {
        int indiceUltimoItem = itemCartao.getParcelas().size() - 1;
        BigDecimal valorTotalItem = itemCartao.getValorTotal();
        BigDecimal valorTotalParcelas = itemCartao.getParcelas().stream()
                .map(Parcela::getValor)
                .reduce(BigDecimal::add)
                .get();

        if (valorTotalParcelas.compareTo(valorTotalItem) > 0) {
            BigDecimal valorParcelaComDesconto = valorTotalParcelas.subtract(valorTotalItem)
                    .setScale(5, RoundingMode.HALF_EVEN);

            BigDecimal valorUltimaParcela = itemCartao.getParcelas().get(indiceUltimoItem).getValor();
            BigDecimal valorParcelaCorrigido = valorUltimaParcela.subtract(valorParcelaComDesconto);
            itemCartao.getParcelas().get(indiceUltimoItem).setValor(valorParcelaCorrigido);
        }
    }
}
