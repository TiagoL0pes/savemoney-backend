package com.savemoney.rest.controllers;

import br.com.six2six.fixturefactory.Fixture;
import com.savemoney.domain.models.Despesa;
import com.savemoney.domain.pagination.DespesasPagination;
import com.savemoney.domain.requests.DespesaRequest;
import com.savemoney.domain.responses.DespesaResponse;
import com.savemoney.rest.facades.DespesaFacade;
import com.savemoney.rest.filters.FiltroPaginacao;
import com.savemoney.templates.models.DespesaTemplate;
import com.savemoney.templates.requests.DespesaRequestTemplate;
import com.savemoney.templates.responses.DespesaResponseTemplate;
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
public class DespesaControllerTest {

    @Mock
    private DespesaFacade facade;

    @InjectMocks
    private DespesaController controller;

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
    public void deveAdicionarDespesa() {
        DespesaRequest request = Fixture.from(DespesaRequest.class)
                .gimme(DespesaRequestTemplate.VALIDO);
        Despesa despesa = Fixture.from(Despesa.class)
                .gimme(DespesaTemplate.PENDENTE);

        Mockito.when(facade.adicionar(anyString(), any(DespesaRequest.class)))
                .thenReturn(despesa);

        ResponseEntity<Void> response = controller.adicionar(token, request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void deveBuscarDespesaPorId() {
        DespesaResponse despesaResponse = Fixture.from(DespesaResponse.class)
                .gimme(DespesaResponseTemplate.VALIDO);
        final Long idDespesa = 1L;

        Mockito.when(facade.buscarPorId(anyString(), anyLong()))
                .thenReturn(despesaResponse);

        ResponseEntity<DespesaResponse> response =
                controller.buscarPorId(token, idDespesa);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void deveListarDespesas() {
        List<DespesaResponse> despesaResponse = Fixture.from(DespesaResponse.class)
                .gimme(1, DespesaResponseTemplate.VALIDO);
        final int mes = 2020;
        final int ano = 10;
        final int pagina = 0;
        final int tamanho = 10;

        DespesasPagination cartoesCreditoPagination =
                new DespesasPagination(despesaResponse);
        Pageable paginacao = PageRequest.of(pagina, tamanho);

        Mockito.when(facade.listar(anyString(), any(FiltroPaginacao.class), any(Pageable.class)))
                .thenReturn(cartoesCreditoPagination);

        ResponseEntity<DespesasPagination> response = controller.listar(token, mes, ano, paginacao);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void deveAtualizarDespesa() {
        DespesaRequest request = Fixture.from(DespesaRequest.class)
                .gimme(DespesaRequestTemplate.VALIDO);
        DespesaResponse despesaResponse = Fixture.from(DespesaResponse.class)
                .gimme(DespesaResponseTemplate.VALIDO);
        final Long idDespesa = 1L;

        Mockito.when(facade.atualizar(anyString(), anyLong(), any(DespesaRequest.class)))
                .thenReturn(despesaResponse);

        ResponseEntity<DespesaResponse> response = controller.atualizar(token, idDespesa, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void deveRemoverDespesa() {
        final Long idDespesa = 1L;

        Mockito.doNothing().when(facade).remover(anyString(), anyLong());

        ResponseEntity<Void> response = controller.remover(token, idDespesa);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
