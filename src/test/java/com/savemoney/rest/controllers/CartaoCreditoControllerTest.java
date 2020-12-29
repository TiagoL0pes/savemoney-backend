package com.savemoney.rest.controllers;

import br.com.six2six.fixturefactory.Fixture;
import com.savemoney.domain.models.CartaoCredito;
import com.savemoney.domain.pagination.CartoesCreditoPagination;
import com.savemoney.domain.requests.CartaoCreditoRequest;
import com.savemoney.domain.requests.ItemCartaoRequest;
import com.savemoney.domain.responses.CartaoCreditoResponse;
import com.savemoney.domain.responses.ItemCartaoResponse;
import com.savemoney.domain.responses.ResumoItemCartaoResponse;
import com.savemoney.rest.facades.CartaoCreditoFacade;
import com.savemoney.templates.models.CartaoCreditoTemplate;
import com.savemoney.templates.requests.CartaoCreditoRequestTemplate;
import com.savemoney.templates.requests.ItemCartaoRequestTemplate;
import com.savemoney.templates.responses.CartaoCreditoResponseTemplate;
import com.savemoney.templates.responses.ItemCartaoResponseTemplate;
import com.savemoney.templates.responses.ResumoItemCartaoResponseTemplate;
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

import java.util.List;

import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class CartaoCreditoControllerTest {

    @Mock
    private CartaoCreditoFacade facade;

    @InjectMocks
    private CartaoCreditoController controller;

    private String token;

    @BeforeEach
    public void setup() {
        loadTemplates("com.savemoney.templates");
        MockHttpServletRequest request = new MockHttpServletRequest();
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ7XCJpZENvbnRhQmFuY2FyaWFcIjoxLFwiZW1haWxcIjpcImFkbWluQGVtYWlsLmNvbVwiLFwiYWdlbmNpYVwiOlwiMDAwMVwiLFwiY29udGFcIjpcIjEyMzQ1NlwifSIsImV4cCI6MTYwOTAwMjU4Mn0.6-Cf6-U41ETexS7qAQt7K_iMCy8xDT_3PAfdxTQG8E9bYV0Law6V_8ld_qwzlrsB8K2ShBTKy7A02TWXEGfStQ";
    }

    @Test
    public void deveAdicionarCartaoCredito() {
        CartaoCreditoRequest request = Fixture.from(CartaoCreditoRequest.class)
                .gimme(CartaoCreditoRequestTemplate.VALIDO);
        CartaoCredito contaBancaria = Fixture.from(CartaoCredito.class)
                .gimme(CartaoCreditoTemplate.VALIDO);

        Mockito.when(facade.adicionar(anyString(), any(CartaoCreditoRequest.class)))
                .thenReturn(contaBancaria);

        ResponseEntity<Void> response = controller.adicionar(token, request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void deveBuscarCartaoCreditoPorId() {
        CartaoCreditoResponse cartaoCreditoResponse = Fixture.from(CartaoCreditoResponse.class)
                .gimme(CartaoCreditoResponseTemplate.VALIDO);
        final Long idCartao = 1L;

        Mockito.when(facade.buscarPorId(anyString(), anyLong()))
                .thenReturn(cartaoCreditoResponse);

        ResponseEntity<CartaoCreditoResponse> response =
                controller.buscarPorId(token, idCartao);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void deveListarCartoesCredito() {
        List<CartaoCreditoResponse> cartaoCreditoResponse = Fixture.from(CartaoCreditoResponse.class)
                .gimme(1, CartaoCreditoResponseTemplate.VALIDO);
        final int pagina = 0;
        final int tamanho = 10;

        CartoesCreditoPagination cartoesCreditoPagination =
                new CartoesCreditoPagination(cartaoCreditoResponse);
        Pageable paginacao = PageRequest.of(pagina, tamanho);

        Mockito.when(facade.listar(anyString(), any(Pageable.class)))
                .thenReturn(cartoesCreditoPagination);

        ResponseEntity<CartoesCreditoPagination> response = controller.listar(token, paginacao);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void deveAtualizarCartaoCredito() {
        CartaoCreditoRequest request = Fixture.from(CartaoCreditoRequest.class)
                .gimme(CartaoCreditoRequestTemplate.VALIDO);
        final Long idCartao = 1L;

        Mockito.doNothing().when(facade)
                .atualizar(anyString(), anyLong(), any(CartaoCreditoRequest.class));

        ResponseEntity<Void> response = controller.atualizar(token, idCartao, request);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void deveRemoverCartaoCredito() {
        final Long idCartao = 1L;

        Mockito.doNothing().when(facade).remover(anyString(), anyLong());

        ResponseEntity<Void> response = controller.remover(token, idCartao);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void deveRetornarResumoItensDoCartao() {
        ResumoItemCartaoResponse resumoItens = Fixture.from(ResumoItemCartaoResponse.class)
                .gimme(ResumoItemCartaoResponseTemplate.VALIDO);
        final Long idCartao = 1L;
        final Integer mes = 10;
        final Integer ano = 2020;

        Mockito.when(facade.resumoItensCartao(anyString(), anyLong(), anyInt(), anyInt()))
                .thenReturn(resumoItens);

        ResponseEntity<ResumoItemCartaoResponse> response =
                controller.resumoItensCartao(token, idCartao, mes, ano);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void deveAdicionarItemCartaoCredito() {
        ItemCartaoRequest request = Fixture.from(ItemCartaoRequest.class)
                .gimme(ItemCartaoRequestTemplate.VALIDO);
        final Long idCartao = 1L;

        Mockito.doNothing().when(facade)
                .adicionarItem(anyString(), anyLong(), any(ItemCartaoRequest.class));

        ResponseEntity<Void> response = controller.adicionarItem(token, idCartao, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void deveBuscarItemCartaoCreditoPorId() {
        ItemCartaoResponse itemResponse = Fixture.from(ItemCartaoResponse.class)
                .gimme(ItemCartaoResponseTemplate.VALIDO);
        final Long idItem = 1L;

        Mockito.when(facade.buscarItemPorId(anyString(), anyLong()))
                .thenReturn(itemResponse);

        ResponseEntity<ItemCartaoResponse> response =
                controller.buscarItemPorId(token, idItem);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void deveAtualizarItemCartaoCredito() {
        ItemCartaoRequest request = Fixture.from(ItemCartaoRequest.class)
                .gimme(ItemCartaoRequestTemplate.VALIDO);
        final Long idCartao = 1L;
        final Long idItem = 1L;

        Mockito.doNothing().when(facade)
                .atualizarItem(anyString(), anyLong(), anyLong(), any(ItemCartaoRequest.class));

        ResponseEntity<Void> response = controller.atualizarItem(token, idCartao, idItem, request);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void deveRemoverItemCartaoCredito() {
        final Long idCartao = 1L;

        Mockito.doNothing().when(facade).removerItemPorId(anyString(), anyLong());

        ResponseEntity<Void> response = controller.removerItemPorId(token, idCartao);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
