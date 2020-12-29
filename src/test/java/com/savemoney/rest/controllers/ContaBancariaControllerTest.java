package com.savemoney.rest.controllers;

import br.com.six2six.fixturefactory.Fixture;
import com.savemoney.abstracts.AbstractController;
import com.savemoney.domain.models.ContaBancaria;
import com.savemoney.domain.pagination.ContasBancariasPagination;
import com.savemoney.domain.requests.ContaBancariaRequest;
import com.savemoney.domain.requests.TransacaoRequest;
import com.savemoney.domain.responses.ContaBancariaResponse;
import com.savemoney.rest.facades.ContaBancariaFacade;
import com.savemoney.templates.models.ContaBancariaTemplate;
import com.savemoney.templates.requests.ContaBancariaRequestTemplate;
import com.savemoney.templates.requests.TransacaoRequestTemplate;
import com.savemoney.templates.responses.BancoResponseTemplate;
import com.savemoney.templates.responses.ContaBancariaResponseTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.URI;
import java.util.List;

import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ContaBancariaControllerTest {

    @Mock
    private ContaBancariaFacade facade;

    @InjectMocks
    private ContaBancariaController controller;

    @BeforeEach
    public void setup() {
        loadTemplates("com.savemoney.templates");
        MockHttpServletRequest request = new MockHttpServletRequest();
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
    }

    @Test
    public void deveAdicionarContaBancaria() {
        ContaBancariaRequest request = Fixture.from(ContaBancariaRequest.class)
                .gimme(ContaBancariaRequestTemplate.VALIDO);
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);

        Mockito.when(facade.adicionar(any(ContaBancariaRequest.class))).thenReturn(contaBancaria);

        ResponseEntity<Void> response = controller.adicionar(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void deveBuscarContaBancariaPorId() {
        ContaBancariaResponse contaBancariaResponse = Fixture.from(ContaBancariaResponse.class)
                .gimme(ContaBancariaResponseTemplate.VALIDO);
        final Long idContaBancaria = 1L;

        Mockito.when(facade.buscarPorId(anyLong())).thenReturn(contaBancariaResponse);

        ResponseEntity<ContaBancariaResponse> response = controller.buscarPorId(idContaBancaria);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void deveListarContasBancarias() {
        List<ContaBancariaResponse> contasBancarias = Fixture.from(ContaBancariaResponse.class)
                .gimme(1, BancoResponseTemplate.VALIDO);
        final int pagina = 0;
        final int tamanho = 10;

        ContasBancariasPagination paginaContasBancarias = new ContasBancariasPagination(contasBancarias);
        Pageable paginacao = PageRequest.of(pagina, tamanho);

        Mockito.when(facade.listar(any(Pageable.class))).thenReturn(paginaContasBancarias);

        ResponseEntity<ContasBancariasPagination> response = controller.listar(paginacao);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void deveAtualizarContaBancaria() {
        ContaBancariaRequest request = Fixture.from(ContaBancariaRequest.class)
                .gimme(ContaBancariaRequestTemplate.VALIDO);
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        final Long idContaBancaria = 1L;

        Mockito.when(facade.atualizar(anyLong(), any(ContaBancariaRequest.class))).thenReturn(contaBancaria);

        ResponseEntity<Void> response = controller.atualizar(idContaBancaria, request);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void deveRemoverContaBancaria() {
        final Long idContaBancaria = 1L;

        Mockito.doNothing().when(facade).remover(anyLong());

        ResponseEntity<Void> response = controller.remover(idContaBancaria);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void deveDepositarNaContaBancaria() {
        TransacaoRequest request = Fixture.from(TransacaoRequest.class)
                .gimme(TransacaoRequestTemplate.ENTRADA);
        final Long idContaBancaria = 1L;

        Mockito.doNothing().when(facade).realizarTransacao(anyLong(), any(TransacaoRequest.class));

        ResponseEntity<Void> response = controller.depositar(idContaBancaria, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void deveSacarDaContaBancaria() {
        TransacaoRequest request = Fixture.from(TransacaoRequest.class)
                .gimme(TransacaoRequestTemplate.SAIDA);
        final Long idContaBancaria = 1L;

        Mockito.doNothing().when(facade).realizarTransacao(anyLong(), any(TransacaoRequest.class));

        ResponseEntity<Void> response = controller.sacar(idContaBancaria, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
