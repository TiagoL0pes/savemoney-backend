package com.savemoney.rest.controllers;

import com.savemoney.rest.facades.PagamentoFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PagamentoControllerTest {

    @Mock
    private PagamentoFacade facade;

    @InjectMocks
    private PagamentoController controller;

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
    public void devePagarDespesa() {
        final Long idDespesa = 1L;

        ResponseEntity<Void> response = controller.pagarDespesa(token, idDespesa);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void devePagarFatura() {
        final Long idFatura = 1L;

        ResponseEntity<Void> response = controller.pagarFatura(token, idFatura);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
