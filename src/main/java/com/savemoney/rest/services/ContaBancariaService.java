package com.savemoney.rest.services;

import com.savemoney.abstracts.AbstractService;
import com.savemoney.domain.models.ContaBancaria;
import com.savemoney.domain.requests.ContaBancariaRequest;
import com.savemoney.rest.repositories.BancoRepository;
import com.savemoney.rest.repositories.ContaBancariaRepository;
import com.savemoney.security.domain.responses.TokenPayloadResponse;
import com.savemoney.security.utils.JwtUtil;
import com.savemoney.utils.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ContaBancariaService extends AbstractService {

    @Autowired
    private ContaBancariaRepository contaBancariaRepository;

    @Autowired
    private BancoRepository bancoRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public ContaBancaria adicionar(ContaBancaria contaBancaria) {
        return contaBancariaRepository.save(contaBancaria);
    }

    public ContaBancaria buscarPorId(Long id) {
        return contaBancariaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conta bancária não encontrada"));
    }

    public Page<ContaBancaria> listar(Pageable pageable) {
        return contaBancariaRepository.findAll(pageable);
    }

    public ContaBancaria atualizar(Long id, ContaBancariaRequest request) {
        ContaBancaria entity = buscarPorId(id);
        alterarBanco(entity, request.getIdBanco());
        entity.setAgencia(validaTexto(entity.getAgencia(), request.getAgencia()));
        entity.setConta(validaTexto(entity.getConta(), request.getConta()));
        entity.setSaldo(validaNumero(entity.getSaldo(), request.getSaldo()));
        return contaBancariaRepository.save(entity);
    }

    public void atualizarSaldo(ContaBancaria contaBancaria) {
        contaBancariaRepository.save(contaBancaria);
    }

    public void remover(Long id) {
        ContaBancaria contaBancaria = buscarPorId(id);
        contaBancariaRepository.delete(contaBancaria);
    }

    public ContaBancaria recuperarContaBancaria(String token) {
        TokenPayloadResponse payload = jwtUtil.getPayloadFromToken(token);
        return buscarPorId(payload.getIdContaBancaria());
    }

    private void alterarBanco(ContaBancaria contaBancaria, Long bancoId) {
        bancoRepository.findById(bancoId)
                .ifPresent(contaBancaria::setBanco);
    }
}

