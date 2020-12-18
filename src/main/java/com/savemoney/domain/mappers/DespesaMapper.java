package com.savemoney.domain.mappers;

import com.savemoney.domain.enums.StatusPagamento;
import com.savemoney.domain.models.Despesa;
import com.savemoney.domain.models.Fatura;
import com.savemoney.domain.models.Parcela;
import com.savemoney.domain.requests.DespesaRequest;
import com.savemoney.domain.responses.DespesaResponse;
import com.savemoney.utils.helpers.DateHelper;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.util.List;

@Mapper(imports = {LocalDate.class, StatusPagamento.class})
public interface DespesaMapper {

    @Mapping(target = "dataEntrada", source = "request.dataEntrada", qualifiedByName = "salvarDataEntrada")
    @Mapping(target = "dataVencimento", source = "request.dataVencimento", qualifiedByName = "salvarDataVencimento")
    @Mapping(target = "descricao", source = "request.descricao")
    @Mapping(target = "valor", source = "request.valor")
    @Mapping(target = "statusPagamento", expression = "java(StatusPagamento.PENDENTE)")
    Despesa toDespesa(DespesaRequest request);

    @Mapping(target = "idDespesa", source = "parcela.idParcela")
    @Mapping(target = "dataEntrada", expression = "java(LocalDate.now())")
    Despesa toDespesa(Parcela parcela);

    @Mapping(target = "idDespesa", source = "fatura.idFatura")
    @Mapping(target = "valor", source = "fatura.total")
    Despesa toDespesa(Fatura fatura);

    DespesaResponse toDespesaResponse(Despesa despesa);

    List<DespesaResponse> toDespesasResponse(List<Despesa> despesas);

    @Named("salvarDataEntrada")
    default LocalDate salvarDataEntrada(String dataEntrada) {
        return StringUtils.isNotEmpty(dataEntrada) ?
                DateHelper.toLocalDate(dataEntrada) : LocalDate.now();
    }

    @Named("salvarDataVencimento")
    default LocalDate salvarDataVencimento(String dataEntrada) {
        return StringUtils.isNotEmpty(dataEntrada) ?
                DateHelper.toLocalDate(dataEntrada) : LocalDate.now().plusMonths(1);
    }
}
