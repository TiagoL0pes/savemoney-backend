package com.savemoney.rest.services;

import com.savemoney.domain.mappers.BancoMapper;
import com.savemoney.domain.models.Banco;
import com.savemoney.domain.pagination.BancosPagination;
import com.savemoney.domain.responses.BancoResponse;
import com.savemoney.rest.repositories.BancoRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BancoService {

    @Autowired
    private BancoRepository bancoRepository;

    private final BancoMapper bancoMapper =
            Mappers.getMapper(BancoMapper.class);

    public BancosPagination listar(Pageable pageable) {
        Page<Banco> page = bancoRepository.findAll(pageable);
        List<BancoResponse> bancos = bancoMapper.toBancosResponse(page.getContent());
        return new BancosPagination(bancos, page.getPageable(), page.getTotalElements());
    }
}
