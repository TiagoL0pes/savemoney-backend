package com.savemoney.rest.services;

import br.com.six2six.fixturefactory.Fixture;
import com.savemoney.domain.models.CartaoCredito;
import com.savemoney.domain.models.ContaBancaria;
import com.savemoney.domain.models.ItemCartao;
import com.savemoney.domain.pagination.CartoesCreditoPagination;
import com.savemoney.domain.requests.ItemCartaoRequest;
import com.savemoney.domain.responses.ResumoItemCartaoResponse;
import com.savemoney.rest.filters.FiltroPaginacao;
import com.savemoney.rest.repositories.CartaoCreditoRepository;
import com.savemoney.rest.repositories.ContaBancariaRepository;
import com.savemoney.rest.repositories.ItemCartaoRepository;
import com.savemoney.templates.models.CartaoCreditoTemplate;
import com.savemoney.templates.models.ContaBancariaTemplate;
import com.savemoney.templates.models.ItemCartaoTemplate;
import com.savemoney.templates.requests.ItemCartaoRequestTemplate;
import com.savemoney.utils.exceptions.BadRequestException;
import com.savemoney.utils.exceptions.ResourceNotFoundException;
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
import java.util.Optional;

import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class CartaoCreditoServiceTest {

    @Mock
    private CartaoCreditoRepository cartaoCreditoRepository;

    @Mock
    private ItemCartaoRepository itemCartaoRepository;

    @Mock
    private ContaBancariaRepository contaBancariaRepository;

    @InjectMocks
    private CartaoCreditoService cartaoCreditoService;

    @BeforeEach
    public void setup() {
        loadTemplates("com.savemoney.templates");
    }

    @Test
    public void deveAdicionarCartaoCredito() {
        CartaoCredito cartaoCredito = Fixture.from(CartaoCredito.class)
                .gimme(CartaoCreditoTemplate.VALIDO);

        Mockito.when(cartaoCreditoRepository.save(any(CartaoCredito.class)))
                .thenReturn(cartaoCredito);

        CartaoCredito cartaoCreditoAdicionado = cartaoCreditoService.adicionar(cartaoCredito);

        assertNotNull(cartaoCreditoAdicionado);
    }

    @Test
    public void deveBuscarCartaoCreditoPorId() {
        CartaoCredito cartaoCreditoEsperado = Fixture.from(CartaoCredito.class)
                .gimme(CartaoCreditoTemplate.VALIDO);
        final Long idCartaoCredito = 1L;

        Mockito.when(cartaoCreditoRepository.findById(anyLong()))
                .thenReturn(Optional.of(cartaoCreditoEsperado));

        CartaoCredito cartaoCredito = cartaoCreditoService.buscarPorId(idCartaoCredito);

        assertNotNull(cartaoCredito);
        assertEquals(cartaoCreditoEsperado, cartaoCredito);
    }

    @Test
    public void deveLancarExcecaoQuandoCartaoNaoExisteBuscaPorId() {
        final Long idCartaoCredito = 1L;

        assertThrows(ResourceNotFoundException.class, () -> {
            cartaoCreditoService.buscarPorId(idCartaoCredito);
        });
    }

    @Test
    public void deveBuscarCartaoCreditoPorIdEContaBancaria() {
        CartaoCredito cartaoCreditoEsperado = Fixture.from(CartaoCredito.class)
                .gimme(CartaoCreditoTemplate.VALIDO);
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        final Long idCartaoCredito = 1L;

        Mockito.when(cartaoCreditoRepository.findByIdCartaoCreditoAndContaBancaria(anyLong(), any(ContaBancaria.class)))
                .thenReturn(Optional.of(cartaoCreditoEsperado));

        CartaoCredito cartaoCredito = cartaoCreditoService.buscarPorId(idCartaoCredito, contaBancaria);

        assertNotNull(cartaoCredito);
        assertEquals(cartaoCreditoEsperado, cartaoCredito);
    }

    @Test
    public void deveLancarExcecaoQuandoCartaoNaoExisteBuscaPorIdEContaBancaria() {
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        final Long idCartaoCredito = 1L;


        assertThrows(ResourceNotFoundException.class, () -> {
            cartaoCreditoService.buscarPorId(idCartaoCredito, contaBancaria);
        });
    }

    @Test
    public void deveListarCartoesCredito() {
        List<CartaoCredito> cartoesCredito = Fixture.from(CartaoCredito.class)
                .gimme(1, CartaoCreditoTemplate.VALIDO);
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        final int pagina = 0;
        final int tamanho = 10;

        Pageable pageable = PageRequest.of(pagina, tamanho);
        Page<CartaoCredito> pageCartoesCredito = new PageImpl<>(cartoesCredito);
        Mockito.when(cartaoCreditoRepository.findAllByContaBancaria(any(Pageable.class), any(ContaBancaria.class)))
                .thenReturn(pageCartoesCredito);

        CartoesCreditoPagination cartoesCreditoPagination =
                cartaoCreditoService.listar(pageable, contaBancaria);

        assertNotNull(cartoesCreditoPagination);
    }

    @Test
    public void deveAtualizarCartaoCredito() {
        CartaoCredito cartaoCredito = Fixture.from(CartaoCredito.class)
                .gimme(CartaoCreditoTemplate.VALIDO);
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        final Long idCartao = 1L;

        Mockito.when(cartaoCreditoRepository.findByNumero(anyString()))
                .thenReturn(Optional.of(cartaoCredito));
        Mockito.when(cartaoCreditoRepository.findByIdCartaoCreditoAndContaBancaria(anyLong(), any(ContaBancaria.class)))
                .thenReturn(Optional.of(cartaoCredito));
        Mockito.when(cartaoCreditoRepository.save(any(CartaoCredito.class)))
                .thenReturn(cartaoCredito);

        CartaoCredito cartaoCreditoAtualizado =
                cartaoCreditoService.atualizar(idCartao, cartaoCredito, contaBancaria);

        assertNotNull(cartaoCreditoAtualizado);
    }

    @Test
    public void deveLancarExcecaoAoTentarAdicionarCartaoJaCadastrado() {
        CartaoCredito cartaoCredito = Fixture.from(CartaoCredito.class)
                .gimme(CartaoCreditoTemplate.VALIDO);
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        final Long idCartao = 2L;

        Mockito.when(cartaoCreditoRepository.findByNumero(anyString()))
                .thenReturn(Optional.of(cartaoCredito));

        assertThrows(BadRequestException.class, () -> {
            cartaoCreditoService.atualizar(idCartao, cartaoCredito, contaBancaria);
        });
    }

    @Test
    public void deveRemoverCartaoCredito() {
        CartaoCredito cartaoCredito = Fixture.from(CartaoCredito.class)
                .gimme(CartaoCreditoTemplate.VALIDO);
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        final Long idContaBancaria = 1L;

        Mockito.when(cartaoCreditoRepository.findByIdCartaoCreditoAndContaBancaria(anyLong(), any(ContaBancaria.class)))
                .thenReturn(Optional.of(cartaoCredito));

        cartaoCreditoService.remover(idContaBancaria, contaBancaria);
    }

    @Test
    public void deveRetornarResumoItensCartao() {
        CartaoCredito cartaoCredito = Fixture.from(CartaoCredito.class)
                .gimme(CartaoCreditoTemplate.VALIDO);
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        final Long idCartao = 1L;
        final Integer mes = 01;
        final Integer ano = 2020;

        FiltroPaginacao filtro = new FiltroPaginacao(mes, ano);
        Mockito.when(cartaoCreditoRepository.findByIdCartaoCreditoAndContaBancaria(anyLong(), any(ContaBancaria.class)))
                .thenReturn(Optional.of(cartaoCredito));

        ResumoItemCartaoResponse resumoItemCartaoResponse =
                cartaoCreditoService.resumoItensCartao(idCartao, filtro, contaBancaria);

        assertNotNull(resumoItemCartaoResponse);
    }

    @Test
    public void deveValidarParcelasComFiltroAnoErrado() {
        CartaoCredito cartaoCredito = Fixture.from(CartaoCredito.class)
                .gimme(CartaoCreditoTemplate.VALIDO);
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        final Long idCartao = 1L;
        final Integer mes = 02;
        final Integer ano = 2021;

        FiltroPaginacao filtro = new FiltroPaginacao(mes, ano);
        Mockito.when(cartaoCreditoRepository.findByIdCartaoCreditoAndContaBancaria(anyLong(), any(ContaBancaria.class)))
                .thenReturn(Optional.of(cartaoCredito));

        ResumoItemCartaoResponse resumoItemCartaoResponse =
                cartaoCreditoService.resumoItensCartao(idCartao, filtro, contaBancaria);

        assertNotNull(resumoItemCartaoResponse);
    }

    @Test
    public void deveAdicionarItemCartaoCredito() {
        CartaoCredito cartaoCredito = Fixture.from(CartaoCredito.class)
                .gimme(CartaoCreditoTemplate.VALIDO);
        ItemCartaoRequest request = Fixture.from(ItemCartaoRequest.class)
                .gimme(ItemCartaoRequestTemplate.VALIDO);
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        final Long idCartao = 1L;

        Mockito.when(cartaoCreditoRepository.findByIdCartaoCreditoAndContaBancaria(anyLong(), any(ContaBancaria.class)))
                .thenReturn(Optional.of(cartaoCredito));

        cartaoCreditoService.adicionarItem(idCartao, request, contaBancaria);
    }

    @Test
    public void deveLancarExcecaoQuandoCartaoCreditoNaoExisteAoAdicionarItem() {
        ItemCartaoRequest request = Fixture.from(ItemCartaoRequest.class)
                .gimme(ItemCartaoRequestTemplate.VALIDO);
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        final Long idCartao = 1L;

        assertThrows(ResourceNotFoundException.class, () -> {
            cartaoCreditoService.adicionarItem(idCartao, request, contaBancaria);
        });
    }

    @Test
    public void deveBuscarItemCartaoCreditoPorId() {
        ItemCartao itemEsperado = Fixture.from(ItemCartao.class)
                .gimme(ItemCartaoTemplate.VALIDO);
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        final Long idItem = 1L;

        Mockito.when(itemCartaoRepository.findByIdItemCartaoAndContaBancaria(anyLong(), any(ContaBancaria.class)))
                .thenReturn(Optional.of(itemEsperado));

        ItemCartao itemCartao = cartaoCreditoService.buscarItemPorId(idItem, contaBancaria);

        assertNotNull(itemCartao);
        assertEquals(itemEsperado, itemCartao);
    }

    @Test
    public void deveLancarExcecaoQuandoItemNaoExisteNaBuscaPorId() {
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        final Long idItem = 1L;

        assertThrows(ResourceNotFoundException.class, () -> {
            cartaoCreditoService.buscarItemPorId(idItem, contaBancaria);
        });
    }

    @Test
    public void deveAtualizarItemCartaoCredito() {
        CartaoCredito cartaoCredito = Fixture.from(CartaoCredito.class)
                .gimme(CartaoCreditoTemplate.VALIDO);
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        ItemCartaoRequest request = Fixture.from(ItemCartaoRequest.class)
                .gimme(ItemCartaoRequestTemplate.VALIDO);
        final Long idCartao = 1L;
        final Long idItem = 1L;

        Mockito.when(cartaoCreditoRepository.findByIdCartaoCreditoAndContaBancaria(anyLong(), any(ContaBancaria.class)))
                .thenReturn(Optional.of(cartaoCredito));
        Mockito.when(cartaoCreditoRepository.save(any(CartaoCredito.class)))
                .thenReturn(cartaoCredito);

        CartaoCredito cartaoCreditoAtualizado =
                cartaoCreditoService.atualizarItem(idCartao, idItem, request, contaBancaria);

        assertNotNull(cartaoCreditoAtualizado);
    }

    @Test
    public void deveLancarExcecaoAoTentarAtualizarItemNaoExistente() {
        CartaoCredito cartaoCredito = Fixture.from(CartaoCredito.class)
                .gimme(CartaoCreditoTemplate.VALIDO);
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        ItemCartaoRequest request = Fixture.from(ItemCartaoRequest.class)
                .gimme(ItemCartaoRequestTemplate.VALIDO);
        final Long idCartao = 1L;
        final Long idItem = 2L;

        Mockito.when(cartaoCreditoRepository.findByIdCartaoCreditoAndContaBancaria(anyLong(), any(ContaBancaria.class)))
                .thenReturn(Optional.of(cartaoCredito));

        assertThrows(ResourceNotFoundException.class, () -> {
            cartaoCreditoService.atualizarItem(idCartao, idItem, request, contaBancaria);
        });
    }

    @Test
    public void deveRemoverItemCartaoCreditoPorId() {
        ItemCartao item = Fixture.from(ItemCartao.class)
                .gimme(ItemCartaoTemplate.VALIDO);
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        final Long idItem = 1L;

        Mockito.when(itemCartaoRepository.findByIdItemCartaoAndContaBancaria(anyLong(), any(ContaBancaria.class)))
                .thenReturn(Optional.of(item));

        cartaoCreditoService.removerItemPorId(idItem, contaBancaria);
    }

    @Test
    public void deveLancarExcecaoQuandoItemNaoExiste() {
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        final Long idItem = 1L;

        assertThrows(ResourceNotFoundException.class, () -> {
            cartaoCreditoService.removerItemPorId(idItem, contaBancaria);
        });
    }

}
