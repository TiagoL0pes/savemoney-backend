package com.savemoney.rest.facades;

import br.com.six2six.fixturefactory.Fixture;
import com.savemoney.domain.models.CartaoCredito;
import com.savemoney.domain.models.ContaBancaria;
import com.savemoney.domain.models.ItemCartao;
import com.savemoney.domain.pagination.CartoesCreditoPagination;
import com.savemoney.domain.requests.CartaoCreditoRequest;
import com.savemoney.domain.requests.ItemCartaoRequest;
import com.savemoney.domain.responses.CartaoCreditoResponse;
import com.savemoney.domain.responses.ItemCartaoResponse;
import com.savemoney.rest.services.CartaoCreditoService;
import com.savemoney.rest.services.ContaBancariaService;
import com.savemoney.templates.models.CartaoCreditoTemplate;
import com.savemoney.templates.models.ContaBancariaTemplate;
import com.savemoney.templates.models.ItemCartaoTemplate;
import com.savemoney.templates.requests.CartaoCreditoRequestTemplate;
import com.savemoney.templates.requests.ItemCartaoRequestTemplate;
import com.savemoney.templates.responses.CartaoCreditoResponseTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class CartaoCreditoFacadeTest {

    @Mock
    private CartaoCreditoService cartaoCreditoService;

    @Mock
    private ContaBancariaService contaBancariaService;

    @InjectMocks
    private CartaoCreditoFacade cartaoCreditoFacade;

    private String token;

    @BeforeEach
    public void setup() {
        loadTemplates("com.savemoney.templates");
        token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ7XCJpZENvbnRhQmFuY2FyaWFcIjoxLFwiZW1haWxcIjpcImFkbWluQGVtYWlsLmNvbVwiLFwiYWdlbmNpYVwiOlwiMDAwMVwiLFwiY29udGFcIjpcIjEyMzQ1NlwifSIsImV4cCI6MTYwOTAwMjU4Mn0.6-Cf6-U41ETexS7qAQt7K_iMCy8xDT_3PAfdxTQG8E9bYV0Law6V_8ld_qwzlrsB8K2ShBTKy7A02TWXEGfStQ";
    }

    @Test
    public void deveAdicionarCartaoCredito() {
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        CartaoCredito cartaoCredito = Fixture.from(CartaoCredito.class)
                .gimme(CartaoCreditoTemplate.VALIDO);
        CartaoCreditoRequest request = Fixture.from(CartaoCreditoRequest.class)
                .gimme(CartaoCreditoRequestTemplate.VALIDO);

        Mockito.when(contaBancariaService.recuperarContaBancaria(anyString()))
                .thenReturn(contaBancaria);
        Mockito.when(cartaoCreditoService.adicionar(any(CartaoCredito.class)))
                .thenReturn(cartaoCredito);

        CartaoCredito cartaoCreditoAdicionado =
                cartaoCreditoFacade.adicionar(token, request);

        assertNotNull(cartaoCreditoAdicionado);
    }

    @Test
    public void deveBuscarCartaoCreditoPorId() {
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        CartaoCredito cartaoCredito = Fixture.from(CartaoCredito.class)
                .gimme(CartaoCreditoTemplate.VALIDO);
        final Long idCartao = 1L;

        Mockito.when(contaBancariaService.recuperarContaBancaria(anyString()))
                .thenReturn(contaBancaria);
        Mockito.when(cartaoCreditoService.buscarPorId(anyLong(), any(ContaBancaria.class)))
                .thenReturn(cartaoCredito);

        CartaoCreditoResponse cartaoCreditoResponse =
                cartaoCreditoFacade.buscarPorId(token, idCartao);

        assertNotNull(cartaoCreditoResponse);
    }

    @Test
    public void deveListarCartoesCredito() {
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        List<CartaoCreditoResponse> cartoesCreditoResponse = Fixture.from(CartaoCreditoResponse.class)
                .gimme(1, CartaoCreditoResponseTemplate.VALIDO);
        final int pagina = 0;
        final int tamanho = 10;

        Pageable pageable = PageRequest.of(pagina, tamanho);
        CartoesCreditoPagination cartoesCreditoPagination =
                new CartoesCreditoPagination(cartoesCreditoResponse);
        Mockito.when(contaBancariaService.recuperarContaBancaria(anyString()))
                .thenReturn(contaBancaria);
        Mockito.when(cartaoCreditoService.listar(any(Pageable.class), any(ContaBancaria.class)))
                .thenReturn(cartoesCreditoPagination);

        CartoesCreditoPagination pagination =
                cartaoCreditoFacade.listar(token, pageable);

        assertNotNull(pagination);
    }

    @Test
    public void deveAtualizarCartaoCredito() {
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        CartaoCredito cartaoCredito = Fixture.from(CartaoCredito.class)
                .gimme(CartaoCreditoTemplate.VALIDO);
        CartaoCreditoRequest request = Fixture.from(CartaoCreditoRequest.class)
                .gimme(CartaoCreditoRequestTemplate.VALIDO);
        final Long idCartao = 1L;

        Mockito.when(contaBancariaService.recuperarContaBancaria(anyString()))
                .thenReturn(contaBancaria);
        Mockito.when(cartaoCreditoService.atualizar(anyLong(), any(CartaoCredito.class), any(ContaBancaria.class)))
                .thenReturn(cartaoCredito);

        cartaoCreditoFacade.atualizar(token, idCartao, request);
    }

    @Test
    public void deveRemoverCartaoCredito() {
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        final Long idCartao = 1L;

        Mockito.when(contaBancariaService.recuperarContaBancaria(anyString()))
                .thenReturn(contaBancaria);

        cartaoCreditoFacade.remover(token, idCartao);
    }

    @Test
    public void deveRetornarResumoItensCartao() {

    }

    @Test
    public void deveAdicionarItemCartaoCredito() {
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        ItemCartaoRequest request = Fixture.from(ItemCartaoRequest.class)
                .gimme(ItemCartaoRequestTemplate.VALIDO);
        final Long idCartao = 1L;

        Mockito.when(contaBancariaService.recuperarContaBancaria(anyString()))
                .thenReturn(contaBancaria);

        cartaoCreditoFacade.adicionarItem(token, idCartao, request);
    }

    @Test
    public void deveBuscarItemCartaoCreditoPorId() {
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        ItemCartao itemCartao = Fixture.from(ItemCartao.class)
                .gimme(ItemCartaoTemplate.VALIDO);
        final Long idCartao = 1L;

        Mockito.when(contaBancariaService.recuperarContaBancaria(anyString()))
                .thenReturn(contaBancaria);
        Mockito.when(cartaoCreditoService.buscarItemPorId(anyLong(), any(ContaBancaria.class)))
                .thenReturn(itemCartao);

        ItemCartaoResponse itemCartaoResponse =
                cartaoCreditoFacade.buscarItemPorId(token, idCartao);

        assertNotNull(itemCartaoResponse);
    }

    @Test
    public void deveAtualizarItemCartaoCredito() {
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        CartaoCredito cartaoCredito = Fixture.from(CartaoCredito.class)
                .gimme(CartaoCreditoTemplate.VALIDO);
        ItemCartaoRequest request = Fixture.from(ItemCartaoRequest.class)
                .gimme(ItemCartaoRequestTemplate.VALIDO);
        final Long idCartao = 1L;
        final Long idItem = 1L;

        Mockito.when(contaBancariaService.recuperarContaBancaria(anyString()))
                .thenReturn(contaBancaria);
        Mockito.when(cartaoCreditoService.atualizarItem(anyLong(), anyLong(), any(ItemCartaoRequest.class), any(ContaBancaria.class)))
                .thenReturn(cartaoCredito);

        cartaoCreditoFacade.atualizarItem(token, idCartao, idItem, request);
    }

    @Test
    public void deveRemoverItemCartaoCredito() {
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        final Long idItem = 1L;

        Mockito.when(contaBancariaService.recuperarContaBancaria(anyString()))
                .thenReturn(contaBancaria);

        cartaoCreditoFacade.removerItemPorId(token, idItem);
    }
}
