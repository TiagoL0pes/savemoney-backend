package com.savemoney.rest.services;

import com.savemoney.abstracts.AbstractService;
import com.savemoney.domain.enums.StatusPagamento;
import com.savemoney.domain.models.ContaBancaria;
import com.savemoney.domain.models.Despesa;
import com.savemoney.domain.requests.DespesaRequest;
import com.savemoney.rest.filters.FiltroPaginacao;
import com.savemoney.rest.repositories.DespesaRepository;
import com.savemoney.rest.repositories.specifications.DespesaSpecification;
import com.savemoney.utils.exceptions.ResourceNotFoundException;
import com.savemoney.utils.exceptions.TransactionNotAllowedException;
import com.savemoney.utils.helpers.DateHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DespesaService extends AbstractService {

    @Autowired
    private DespesaRepository despesaRepository;

    public Despesa adicionar(Despesa despesa) {
        return despesaRepository.save(despesa);
    }

    public Despesa buscarPorId(Long idDespesa, ContaBancaria contaBancaria) {
        return despesaRepository.findByIdDespesaAndContaBancaria(idDespesa, contaBancaria)
                .orElseThrow(() -> new ResourceNotFoundException("Despesa não encontrada"));
    }

    public Page<Despesa> listar(FiltroPaginacao filtro, Pageable pageable) {
        DespesaSpecification despesaSpecification = new DespesaSpecification(filtro);
        return despesaRepository.findAll(despesaSpecification, pageable);
    }

    public void atualizarStatus(Despesa despesa) {
        despesaRepository.save(despesa);
    }

    public Despesa atualizar(Despesa despesa, DespesaRequest request) {
        atualizarDespesa(despesa, request);
        return despesaRepository.save(despesa);
    }

    private void atualizarDespesa(Despesa despesa, DespesaRequest request) {
        despesa.setDataEntrada(StringUtils.isNotBlank(request.getDataEntrada()) ?
                DateHelper.toLocalDate(request.getDataEntrada()) : despesa.getDataEntrada());
        despesa.setDataVencimento(StringUtils.isNotBlank(request.getDataVencimento()) ?
                DateHelper.toLocalDate(request.getDataVencimento()) : despesa.getDataVencimento());
        despesa.setDescricao(StringUtils.isNotBlank(request.getDescricao()) ?
                request.getDescricao() : despesa.getDescricao());
        despesa.setValor(BigDecimal.ZERO.compareTo(request.getValor()) < 0 ?
                request.getValor() : despesa.getValor());
    }

    public void remover(Despesa despesa) {
        if (StatusPagamento.PAGO.equals(despesa.getStatusPagamento())) {
            throw new TransactionNotAllowedException("Não é possível remover uma despesa paga");
        }
        despesaRepository.delete(despesa);
    }
}
