package com.savemoney.rest.controllers;

import br.com.six2six.fixturefactory.Fixture;
import com.savemoney.domain.pagination.BancosPagination;
import com.savemoney.domain.responses.BancoResponse;
import com.savemoney.rest.services.BancoService;
import com.savemoney.templates.responses.BancoResponseTemplate;
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

import java.util.List;

import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class BancoControllerTest {

    @Mock
    private BancoService service;

    @InjectMocks
    private BancoController controller;

    private int pagina;
    private int tamanho;

    @BeforeEach
    public void setup() {
        loadTemplates("com.savemoney.templates");
        this.pagina = 0;
        this.tamanho = 10;
    }

    @Test
    public void deveListarBancos() {
        List<BancoResponse> bancos = Fixture.from(BancoResponse.class)
                .gimme(1, BancoResponseTemplate.VALIDO);

        BancosPagination paginaBancos = new BancosPagination(bancos);
        Pageable paginacao = PageRequest.of(this.pagina, this.tamanho);

        Mockito.when(service.listar(any(Pageable.class))).thenReturn(paginaBancos);

        ResponseEntity<BancosPagination> response = controller.listar(paginacao);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}
