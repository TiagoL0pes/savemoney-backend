package com.savemoney.domain.mappers;

import com.savemoney.domain.models.ContaBancaria;
import com.savemoney.domain.requests.ContaBancariaRequest;
import com.savemoney.domain.responses.ContaBancariaResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ContaBancariaMapper extends DefaultMapper {

    ContaBancaria toContaBancaria(ContaBancariaRequest request);

    ContaBancariaResponse toContaBancariaResponse(ContaBancaria contaBancaria);

    List<ContaBancariaResponse> toContasBancariasResponse(List<ContaBancaria> contasBancarias);
}
