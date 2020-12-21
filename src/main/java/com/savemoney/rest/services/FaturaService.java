package com.savemoney.rest.services;

import com.savemoney.domain.mappers.FaturaMapper;
import com.savemoney.domain.models.Fatura;
import com.savemoney.domain.pagination.FaturasPagination;
import com.savemoney.domain.responses.FaturaResponse;
import com.savemoney.rest.repositories.FaturaRepository;
import com.savemoney.utils.exceptions.ResourceNotFoundException;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FaturaService {

    @Autowired
    private FaturaRepository faturaRepository;

    private final FaturaMapper faturaMapper = Mappers.getMapper(FaturaMapper.class);

    public Fatura gerar(Fatura fatura) {
        return faturaRepository.save(fatura);
    }

    public Fatura buscarPorId(Long id) {
        return faturaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fatura não encontrada"));
    }

    public FaturasPagination listar(Long idCartao, Pageable pageable) {
        Page<Fatura> page = faturaRepository.buscarTodosPorCartaoCredito(idCartao, pageable);
        List<FaturaResponse> content = faturaMapper.toFaturasResponse(page.getContent());
        return new FaturasPagination(content, page.getPageable(), page.getTotalElements());

    }

    public void buscarPorDataVencimento(LocalDate dueDate) {
        Integer mes = dueDate.getMonthValue();
        Integer ano = dueDate.getYear();
        if (faturaRepository.buscarPorDataVencimento(mes, ano).isPresent()) {
            throw new ResourceNotFoundException("Fatura para este mês já foi gerada");
        }
    }

    public void atualizarStatus(Fatura fatura) {
        faturaRepository.save(fatura);
    }
}
