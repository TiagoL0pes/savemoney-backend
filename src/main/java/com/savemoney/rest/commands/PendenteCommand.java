package com.savemoney.rest.commands;

import com.savemoney.domain.enums.StatusPagamento;
import com.savemoney.domain.mappers.TransacaoMapper;
import com.savemoney.domain.models.ContaBancaria;
import com.savemoney.domain.models.Despesa;
import com.savemoney.domain.models.Transacao;
import com.savemoney.domain.requests.TransacaoRequest;
import com.savemoney.rest.services.TransacaoService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PendenteCommand implements PagamentoCommand {

    @Autowired
    private TransacaoService transacaoService;

    private final TransacaoMapper transacaoMapper =
            Mappers.getMapper(TransacaoMapper.class);

    @Override
    public void execute(Despesa despesa, ContaBancaria contaBancaria) {
        adicionarTransacao(despesa, contaBancaria);
        atualizarStatusPagamento(despesa);
    }

    private void adicionarTransacao(Despesa despesa, ContaBancaria contaBancaria) {
        TransacaoRequest request = transacaoMapper.toDadosTransacao(despesa);
        Transacao transacao = transacaoService.gerarNovaTransacao(request);
        contaBancaria.adicionarTransacaoEAtualizarSaldo(transacao);
        transacao.setContaBancaria(contaBancaria);
        transacaoService.adicionar(transacao);
    }

    private void atualizarStatusPagamento(Despesa despesa) {
        despesa.setDataPagamento(LocalDate.now());
        despesa.setStatusPagamento(StatusPagamento.PAGO);
    }
}
