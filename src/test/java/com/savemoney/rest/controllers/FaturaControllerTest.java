package com.savemoney.rest.controllers;

import br.com.six2six.fixturefactory.Fixture;
import com.savemoney.domain.models.Fatura;
import com.savemoney.domain.pagination.FaturasPagination;
import com.savemoney.domain.requests.FaturaRequest;
import com.savemoney.domain.responses.FaturaResponse;
import com.savemoney.rest.facades.FaturaFacade;
import com.savemoney.templates.models.FaturaTemplate;
import com.savemoney.templates.requests.FaturaRequestTemplate;
import com.savemoney.templates.responses.FaturaResponseTemplate;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class FaturaControllerTest {

    @Mock
    private FaturaFacade facade;

    @InjectMocks
    private FaturaController controller;

    @BeforeEach
    public void setup() {
        loadTemplates("com.savemoney.templates");
        MockHttpServletRequest request = new MockHttpServletRequest();
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
    }

    @Test
    public void deveGerarFatura() {
        FaturaRequest request = Fixture.from(FaturaRequest.class)
                .gimme(FaturaRequestTemplate.VALIDO);
        Fatura fatura = Fixture.from(Fatura.class)
                .gimme(FaturaTemplate.VALIDO);

        Mockito.when(facade.gerarFatura(any(FaturaRequest.class))).thenReturn(fatura);

        ResponseEntity<Void> response = controller.gerar(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void deveAtualizarFatura() {
        FaturaRequest request = Fixture.from(FaturaRequest.class)
                .gimme(FaturaRequestTemplate.VALIDO);
        final Long idFatura = 1L;

        ResponseEntity<Void> response = controller.atualizar(idFatura, request);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void deveBuscarFaturaPorId() {
        FaturaResponse faturaResponse = Fixture.from(FaturaResponse.class)
                .gimme(FaturaResponseTemplate.VALIDO);
        final Long idFatura = 1L;

        Mockito.when(facade.buscarPorId(anyLong())).thenReturn(faturaResponse);

        ResponseEntity<FaturaResponse> response = controller.buscarPorId(idFatura);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void deveListarDespesas() {
        List<FaturaResponse> faturaResponse = Fixture.from(FaturaResponse.class)
                .gimme(1, FaturaResponseTemplate.VALIDO);
        final Long idCartao = 1L;
        final int pagina = 0;
        final int tamanho = 10;

        FaturasPagination faturasPagination =
                new FaturasPagination(faturaResponse);
        Pageable paginacao = PageRequest.of(pagina, tamanho);

        Mockito.when(facade.listar(anyLong(), any(Pageable.class)))
                .thenReturn(faturasPagination);

        ResponseEntity<FaturasPagination> response = controller.listar(idCartao, paginacao);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

}
