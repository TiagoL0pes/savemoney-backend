package com.savemoney.domain.mappers;

import com.savemoney.domain.models.ContaBancaria;
import com.savemoney.domain.requests.ContaBancariaRequest;
import com.savemoney.domain.responses.ContaBancariaResponse;
import org.mapstruct.Mapper;

@Mapper
public interface ContaBancariaMapper {

    ContaBancaria toContaBancaria(ContaBancariaRequest request);

    ContaBancariaResponse toContaBancariaResponse(ContaBancaria contaBancaria);
}
