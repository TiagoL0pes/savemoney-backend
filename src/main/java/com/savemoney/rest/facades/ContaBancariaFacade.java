package com.savemoney.rest.facades;

import com.savemoney.domain.mappers.ContaBancariaMapper;
import com.savemoney.domain.models.Banco;
import com.savemoney.domain.models.ContaBancaria;
import com.savemoney.domain.models.Transacao;
import com.savemoney.domain.pagination.ContasBancariasPagination;
import com.savemoney.domain.requests.ContaBancariaRequest;
import com.savemoney.domain.requests.TransacaoRequest;
import com.savemoney.domain.responses.ContaBancariaResponse;
import com.savemoney.rest.services.BancoService;
import com.savemoney.rest.services.ContaBancariaService;
import com.savemoney.rest.services.TransacaoService;
import com.savemoney.security.domain.models.Usuario;
import com.savemoney.security.rest.services.UsuarioService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ContaBancariaFacade {

    @Autowired
    private BancoService bancoService;

    @Autowired
    private ContaBancariaService contaBancariaService;

    @Autowired
    private TransacaoService transacaoService;

    @Autowired
    private UsuarioService usuarioService;

    private final ContaBancariaMapper contaBancariaMapper =
            Mappers.getMapper(ContaBancariaMapper.class);

    public ContaBancaria adicionar(String token, ContaBancariaRequest request) {
        ContaBancaria contaBancaria = contaBancariaMapper.toContaBancaria(request);
        Usuario usuario = usuarioService.recuperarUsuario(token);
        contaBancaria.setUsuario(usuario);

        if (Objects.nonNull(request.getIdBanco())) {
            Banco banco = bancoService.buscarPorId(request.getIdBanco());
            contaBancaria.setBanco(banco);
        }

        return contaBancariaService.adicionar(contaBancaria);
    }

    public ContaBancariaResponse buscarPorId(Long id) {
        ContaBancaria contaBancaria = contaBancariaService.buscarPorId(id);
        return contaBancariaMapper.toContaBancariaResponse(contaBancaria);
    }

    public ContasBancariasPagination listar(Pageable pageable) {
        Page<ContaBancaria> page = contaBancariaService.listar(pageable);
        List<ContaBancariaResponse> response = contaBancariaMapper.toContasBancariasResponse(page.getContent());
        return new ContasBancariasPagination(response, page.getPageable(), page.getTotalElements());
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
