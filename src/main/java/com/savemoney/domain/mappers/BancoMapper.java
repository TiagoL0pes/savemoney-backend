package com.savemoney.domain.mappers;

import com.savemoney.domain.models.Banco;
import com.savemoney.domain.responses.BancoResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface BancoMapper {

    BancoResponse toBancoResponse(Banco banco);

    List<BancoResponse> toBancosResponse(List<Banco> bancos);
}
