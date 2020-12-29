package com.savemoney.rest.facades;

import com.savemoney.domain.mappers.DespesaMapper;
import com.savemoney.domain.models.ContaBancaria;
import com.savemoney.domain.models.Despesa;
import com.savemoney.domain.pagination.DespesasPagination;
import com.savemoney.domain.requests.DespesaRequest;
import com.savemoney.domain.responses.DespesaResponse;
import com.savemoney.rest.filters.FiltroPaginacao;
import com.savemoney.rest.services.ContaBancariaService;
import com.savemoney.rest.services.DespesaService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DespesaFacade {

    @Autowired
    private DespesaService despesaService;

    @Autowired
    private ContaBancariaService contaBancariaService;

    private final DespesaMapper mapper =
            Mappers.getMapper(DespesaMapper.class);

    public Despesa adicionar(String token, DespesaRequest request) {
        ContaBancaria contaBancaria = contaBancariaService.recuperarContaBancaria(token);
        Despesa despesa = mapper.toDespesa(request);
        despesa.setContaBancaria(contaBancaria);

        return despesaService.adicionar(despesa);
    }

    public DespesaResponse buscarPorId(String token, Long idDespesa) {
        ContaBancaria contaBancaria = contaBancariaService.recuperarContaBancaria(token);
        Despesa despesa = despesaService.buscarPorId(idDespesa, contaBancaria);
        return mapper.toDespesaResponse(despesa);
    }

    public DespesasPagination listar(String token, FiltroPaginacao filtro, Pageable pageable) {
        ContaBancaria contaBancaria = contaBancariaService.recuperarContaBancaria(token);
        filtro.setContaBancaria(contaBancaria);

        Page<Despesa> page = despesaService.listar(filtro, pageable);
        List<DespesaResponse> response = mapper.toDespesasResponse(page.getContent());

        return new DespesasPagination(response, page.getPageable(), page.getTotalElements());
    }

    public DespesaResponse atualizar(String token, Long idDespesa, DespesaRequest request) {
        Despesa despesa = buscarDespesaPorId(token, idDespesa);
        Despesa despesaAtualizada = despesaService.atualizar(despesa, request);
        return mapper.toDespesaResponse(despesaAtualizada);
    }

    public void remover(String token, Long idDespesa) {
        Despesa despesa = buscarDespesaPorId(token, idDespesa);
        despesaService.remover(despesa);
    }

    private Despesa buscarDespesaPorId(String token, Long idDespesa) {
        ContaBancaria contaBancaria = contaBancariaService.recuperarContaBancaria(token);
        return despesaService.buscarPorId(idDespesa, contaBancaria);
    }
}
