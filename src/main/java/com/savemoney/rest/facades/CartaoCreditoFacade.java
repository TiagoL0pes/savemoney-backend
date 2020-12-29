package com.savemoney.rest.facades;

import com.savemoney.domain.mappers.CartaoCreditoMapper;
import com.savemoney.domain.models.CartaoCredito;
import com.savemoney.domain.models.ContaBancaria;
import com.savemoney.domain.models.ItemCartao;
import com.savemoney.domain.pagination.CartoesCreditoPagination;
import com.savemoney.domain.requests.CartaoCreditoRequest;
import com.savemoney.domain.requests.ItemCartaoRequest;
import com.savemoney.domain.responses.CartaoCreditoResponse;
import com.savemoney.domain.responses.ItemCartaoResponse;
import com.savemoney.domain.responses.ResumoItemCartaoResponse;
import com.savemoney.rest.filters.FiltroPaginacao;
import com.savemoney.rest.services.CartaoCreditoService;
import com.savemoney.rest.services.ContaBancariaService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CartaoCreditoFacade {

    @Autowired
    private CartaoCreditoService cartaoCreditoService;

    @Autowired
    private ContaBancariaService contaBancariaService;

    private final CartaoCreditoMapper cartaoCreditoMapper =
            Mappers.getMapper(CartaoCreditoMapper.class);

    public CartaoCredito adicionar(String token, CartaoCreditoRequest request) {
        ContaBancaria contaBancaria = contaBancariaService.recuperarContaBancaria(token);
        CartaoCredito cartaoCredito = cartaoCreditoMapper.toCartaoCredito(request);
        cartaoCredito.setContaBancaria(contaBancaria);
        return cartaoCreditoService.adicionar(cartaoCredito);
    }

    public CartaoCreditoResponse buscarPorId(String token, Long idCartao) {
        ContaBancaria contaBancaria = contaBancariaService.recuperarContaBancaria(token);
        CartaoCredito cartaoCredito = cartaoCreditoService.buscarPorId(idCartao, contaBancaria);
        return cartaoCreditoMapper.toCartaoCreditoResponse(cartaoCredito);
    }

    public CartoesCreditoPagination listar(String token, Pageable pageable) {
        ContaBancaria contaBancaria = contaBancariaService.recuperarContaBancaria(token);
        return cartaoCreditoService.listar(pageable, contaBancaria);
    }

    public void atualizar(String token, Long idCartao, CartaoCreditoRequest request) {
        ContaBancaria contaBancaria = contaBancariaService.recuperarContaBancaria(token);
        CartaoCredito cartaoCredito = cartaoCreditoMapper.toCartaoCredito(request);
        cartaoCreditoService.atualizar(idCartao, cartaoCredito, contaBancaria);
    }

    public void remover(String token, Long idCartao) {
        ContaBancaria contaBancaria = contaBancariaService.recuperarContaBancaria(token);
        cartaoCreditoService.remover(idCartao, contaBancaria);
    }

    public ResumoItemCartaoResponse resumoItensCartao(String token, Long idCartao, Integer mes, Integer ano) {
        ContaBancaria contaBancaria = contaBancariaService.recuperarContaBancaria(token);
        FiltroPaginacao filtro = new FiltroPaginacao(mes, ano);
        return cartaoCreditoService.resumoItensCartao(idCartao, filtro, contaBancaria);
    }

    public void adicionarItem(String token, Long idCartao, ItemCartaoRequest request) {
        ContaBancaria contaBancaria = contaBancariaService.recuperarContaBancaria(token);
        cartaoCreditoService.adicionarItem(idCartao, request, contaBancaria);
    }

    public ItemCartaoResponse buscarItemPorId(String token, Long idItem) {
        ContaBancaria contaBancaria = contaBancariaService.recuperarContaBancaria(token);
        ItemCartao itemCartao = cartaoCreditoService.buscarItemPorId(idItem, contaBancaria);
        return cartaoCreditoMapper.toItemCartaoResponse(itemCartao);
    }

    public void atualizarItem(String token, Long idCartao, Long idItem, ItemCartaoRequest request) {
        ContaBancaria contaBancaria = contaBancariaService.recuperarContaBancaria(token);
        cartaoCreditoService.atualizarItem(idCartao, idItem, request, contaBancaria);
    }

    public void removerItemPorId(String token, Long idItem) {
        ContaBancaria contaBancaria = contaBancariaService.recuperarContaBancaria(token);
        cartaoCreditoService.removerItemPorId(idItem, contaBancaria);
    }
}
