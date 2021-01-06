package com.savemoney.rest.facades;

import br.com.six2six.fixturefactory.Fixture;
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
import com.savemoney.templates.models.BancoTemplate;
import com.savemoney.templates.models.ContaBancariaTemplate;
import com.savemoney.templates.models.TransacaoTemplate;
import com.savemoney.templates.models.UsuarioTemplate;
import com.savemoney.templates.requests.ContaBancariaRequestTemplate;
import com.savemoney.templates.requests.TransacaoRequestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ContaBancariaFacadeTest {

    @Mock
    private BancoService bancoService;

    @Mock
    private ContaBancariaService contaBancariaService;

    @Mock
    private TransacaoService transacaoService;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private ContaBancariaFacade contaBancariaFacade;

    private String token;

    @BeforeEach
    public void setup() {
        loadTemplates("com.savemoney.templates");
        token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ7XCJpZENvbnRhQmFuY2FyaWFcIjoxLFwiZW1haWxcIjpcImFkbWluQGVtYWlsLmNvbVwiLFwiYWdlbmNpYVwiOlwiMDAwMVwiLFwiY29udGFcIjpcIjEyMzQ1NlwifSIsImV4cCI6MTYwOTAwMjU4Mn0.6-Cf6-U41ETexS7qAQt7K_iMCy8xDT_3PAfdxTQG8E9bYV0Law6V_8ld_qwzlrsB8K2ShBTKy7A02TWXEGfStQ";
    }

    @Test
    public void deveAdicionarContaBancaria() {
        Banco banco = Fixture.from(Banco.class)
                .gimme(BancoTemplate.VALIDO);
        Usuario usuario = Fixture.from(Usuario.class)
                .gimme(UsuarioTemplate.VALIDO);
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        ContaBancariaRequest request = Fixture.from(ContaBancariaRequest.class)
                .gimme(ContaBancariaRequestTemplate.VALIDO);

        Mockito.when(bancoService.buscarPorId(anyLong()))
                .thenReturn(banco);
        Mockito.when(usuarioService.recuperarUsuario(anyString()))
                .thenReturn(usuario);
        Mockito.when(contaBancariaService.adicionar(any(ContaBancaria.class)))
                .thenReturn(contaBancaria);

        ContaBancaria contaBancariaAdicionada = contaBancariaFacade.adicionar(token, request);

        assertNotNull(contaBancariaAdicionada);
    }

    @Test
    public void deveAdicionarContaBancariaSemBanco() {
        Usuario usuario = Fixture.from(Usuario.class)
                .gimme(UsuarioTemplate.VALIDO);
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        ContaBancariaRequest request = Fixture.from(ContaBancariaRequest.class)
                .gimme(ContaBancariaRequestTemplate.VALIDO);
        request.setIdBanco(null);

        Mockito.when(usuarioService.recuperarUsuario(anyString()))
                .thenReturn(usuario);
        Mockito.when(contaBancariaService.adicionar(any(ContaBancaria.class)))
                .thenReturn(contaBancaria);

        ContaBancaria contaBancariaAdicionada = contaBancariaFacade.adicionar(token, request);

        assertNotNull(contaBancariaAdicionada);
    }

    @Test
    public void deveBuscarContaBancariaPorId() {
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        final Long idContaBancaria = 1L;

        Mockito.when(contaBancariaService.buscarPorId(anyLong()))
                .thenReturn(contaBancaria);

        ContaBancariaResponse contaBancariaResponse =
                contaBancariaFacade.buscarPorId(idContaBancaria);

        assertNotNull(contaBancariaResponse);
    }

    @Test
    public void deveListarContasBancarias() {
        List<ContaBancaria> contasBancarias = Fixture.from(ContaBancaria.class)
                .gimme(1, ContaBancariaTemplate.VALIDO);
        final int pagina = 0;
        final int tamanho = 10;

        Page<ContaBancaria> pageContasBancarias = new PageImpl<>(contasBancarias);
        Mockito.when(contaBancariaService.listar(any(Pageable.class)))
                .thenReturn(pageContasBancarias);

        ContasBancariasPagination contasBancariasPagination =
                contaBancariaFacade.listar(PageRequest.of(pagina, tamanho));

        assertNotNull(contasBancariasPagination);
    }

    @Test
    public void deveAtualizarContaBancaria() {
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        ContaBancariaRequest request = Fixture.from(ContaBancariaRequest.class)
                .gimme(ContaBancariaRequestTemplate.VALIDO);
        final Long idContaBancaria = 1L;

        Mockito.when(contaBancariaService.atualizar(anyLong(), any(ContaBancariaRequest.class)))
                .thenReturn(contaBancaria);

        ContaBancaria contaBancariaAtualizada =
                contaBancariaFacade.atualizar(idContaBancaria, request);

        assertNotNull(contaBancariaAtualizada);
    }

    @Test
    public void deveRemoverContaBancaria() {
        final Long idContaBancaria = 1L;

        contaBancariaFacade.remover(idContaBancaria);
    }

    @Test
    public void deveRealizarTransacao() {
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        Transacao transacao = Fixture.from(Transacao.class)
                .gimme(TransacaoTemplate.ENTRADA);
        TransacaoRequest request = Fixture.from(TransacaoRequest.class)
                .gimme(TransacaoRequestTemplate.ENTRADA);
        final Long idContaBancaria = 1L;

        Mockito.when(contaBancariaService.buscarPorId(anyLong()))
                .thenReturn(contaBancaria);
        Mockito.when(transacaoService.gerarNovaTransacao(any(TransacaoRequest.class)))
                .thenReturn(transacao);
        Mockito.when(transacaoService.adicionar(any(Transacao.class)))
                .thenReturn(transacao);

        contaBancariaFacade.realizarTransacao(idContaBancaria, request);
    }

}
