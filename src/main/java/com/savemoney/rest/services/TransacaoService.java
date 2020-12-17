package com.savemoney.rest.services;

import com.savemoney.domain.models.Transacao;
import com.savemoney.domain.requests.TransacaoRequest;
import com.savemoney.rest.repositories.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    public Transacao adicionar(Transacao transacao) {
        return transacaoRepository.save(transacao);
    }

    public Transacao gerarNovaTransacao(TransacaoRequest request) {
        return new Transacao(LocalDateTime.now(),
                request.getDescricao(),
                request.getValor(),
                request.getTipoTransacao());
    }
}
