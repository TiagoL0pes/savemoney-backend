package com.savemoney.domain.mappers;

import com.savemoney.domain.models.Despesa;
import com.savemoney.domain.models.Fatura;
import com.savemoney.domain.responses.FaturaResponse;
import com.savemoney.utils.helpers.DateHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(imports = {DateHelper.class})
public interface FaturaMapper {

    @Mapping(target = "idFatura", source = "despesa.idDespesa")
    @Mapping(target = "total", source = "despesa.valor")
    Fatura toFatura(Despesa despesa);

    List<FaturaResponse> toFaturasResponse(List<Fatura> faturas);

    @Mapping(target = "dataVencimento",
            expression = "java(DateHelper.formatDate(fatura.getDataVencimento(), \"dd/MM/yyyy\"))")
    FaturaResponse toFaturaResponse(Fatura fatura);
}
