package com.savemoney.domain.mappers;

import com.savemoney.domain.models.Despesa;
import com.savemoney.domain.requests.TransacaoRequest;
import org.mapstruct.Mapper;

@Mapper
public interface TransacaoMapper {

    TransacaoRequest toDadosTransacao(Despesa despesa);
}
