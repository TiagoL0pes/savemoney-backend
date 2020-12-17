package com.savemoney.rest.facades;

import com.savemoney.domain.mappers.ContaBancariaMapper;
import com.savemoney.domain.models.ContaBancaria;
import com.savemoney.domain.models.Transacao;
import com.savemoney.domain.pagination.ContasBancariasPagination;
import com.savemoney.domain.requests.ContaBancariaRequest;
import com.savemoney.domain.requests.TransacaoRequest;
import com.savemoney.rest.services.ContaBancariaService;
import com.savemoney.rest.services.TransacaoService;
import com.savemoney.security.domain.models.Usuario;
import com.savemoney.security.rest.services.UsuarioService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ContaBancariaFacade {

    @Autowired
    private ContaBancariaService contaBancariaService;

    @Autowired
    private TransacaoService transacaoService;

    @Autowired
    private UsuarioService usuarioService;

    private final ContaBancariaMapper contaBancariaMapper =
            Mappers.getMapper(ContaBancariaMapper.class);

    public ContaBancaria adicionar(ContaBancariaRequest request) {
        ContaBancaria contaBancaria = contaBancariaMapper.toContaBancaria(request);
        if (Objects.nonNull(request.getIdBanco())) {
            Usuario usuario = usuarioService.buscarPorId(request.getIdBanco());
            contaBancaria.setUsuario(usuario);
        }

        return contaBancariaService.adicionar(contaBancaria);
    }

    public ContaBancaria buscarPorId(Long id) {
        return contaBancariaService.buscarPorId(id);
    }

    public ContasBancariasPagination listar(Pageable pageable) {
        Page<ContaBancaria> page = contaBancariaService.listar(pageable);
        return new ContasBancariasPagination(page.getContent(), page.getPageable(), page.getTotalElements());
    }

    public ContaBancaria atualizar(Long idContaBancaria, ContaBancariaRequest request) {
        return contaBancariaService.atualizar(idContaBancaria, request);
    }

    public void remover(Long id) {
        contaBancariaService.remover(id);
    }

    public void realizarTransacao(Long id, TransacaoRequest request) {
        ContaBancaria contaBancaria = contaBancariaService.buscarPorId(id);
        Transacao transacao = transacaoService.gerarNovaTransacao(request);
        contaBancaria.adicionarTransacaoEAtualizarSaldo(transacao);
        transacao.setContaBancaria(contaBancaria);
        transacaoService.adicionar(transacao);
        contaBancariaService.atualizarSaldo(contaBancaria);
    }
}
