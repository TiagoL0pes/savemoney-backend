package com.savemoney.rest.services;

import com.savemoney.abstracts.AbstractService;
import com.savemoney.domain.mappers.CartaoCreditoMapper;
import com.savemoney.domain.mappers.ItemCartaoMapper;
import com.savemoney.domain.models.CartaoCredito;
import com.savemoney.domain.models.ContaBancaria;
import com.savemoney.domain.models.ItemCartao;
import com.savemoney.domain.models.Parcela;
import com.savemoney.domain.pagination.CartoesCreditoPagination;
import com.savemoney.domain.requests.ItemCartaoRequest;
import com.savemoney.domain.responses.CartaoCreditoResponse;
import com.savemoney.domain.responses.ResumoItemCartaoResponse;
import com.savemoney.rest.filters.FiltroPaginacao;
import com.savemoney.rest.repositories.CartaoCreditoRepository;
import com.savemoney.rest.repositories.ContaBancariaRepository;
import com.savemoney.rest.repositories.ItemCartaoRepository;
import com.savemoney.utils.exceptions.BadRequestException;
import com.savemoney.utils.exceptions.ResourceNotFoundException;
import com.savemoney.utils.helpers.DateHelper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartaoCreditoService extends AbstractService {

    @Autowired
    private CartaoCreditoRepository cartaoCreditoRepository;

    @Autowired
    private ItemCartaoRepository itemCartaoRepository;

    @Autowired
    private ContaBancariaRepository contaBancariaRepository;

    private final CartaoCreditoMapper cartaoCreditoMapper =
            Mappers.getMapper(CartaoCreditoMapper.class);

    private final ItemCartaoMapper itemCartaoMapper =
            Mappers.getMapper(ItemCartaoMapper.class);

    public CartaoCredito adicionar(CartaoCredito cartaoCredito) {
        return cartaoCreditoRepository.save(cartaoCredito);
    }

    public CartaoCredito buscarPorId(Long id) {
        return cartaoCreditoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cartão de crédito não encontrado"));
    }

    public CartaoCredito buscarPorId(Long id, ContaBancaria contaBancaria) {
        return cartaoCreditoRepository.findByIdCartaoCreditoAndContaBancaria(id, contaBancaria)
                .orElseThrow(() -> new ResourceNotFoundException("Cartão de crédito não encontrado"));
    }

    public CartoesCreditoPagination listar(Pageable pageable, ContaBancaria contaBancaria) {
        Page<CartaoCredito> page = cartaoCreditoRepository.findAllByContaBancaria(pageable, contaBancaria);
        List<CartaoCreditoResponse> content = cartaoCreditoMapper.toCartoesCreditoResponse(page.getContent());
        return new CartoesCreditoPagination(content, page.getPageable(), page.getTotalElements());
    }

    public CartaoCredito atualizar(Long idCartao, CartaoCredito request, ContaBancaria contaBancaria) {
        if (numeroCartaoJaRegistrado(request.getNumero(), idCartao)) {
            throw new BadRequestException("Cartão de crédito já registrado");
        }

        CartaoCredito cartaoCredito = buscarPorId(idCartao, contaBancaria);
        cartaoCredito.setNumero(request.getNumero());
        cartaoCredito.setDiaVencimento(validaNumero(cartaoCredito.getDiaVencimento(), request.getDiaVencimento()));
        cartaoCredito.setLimiteCredito(validaNumero(cartaoCredito.getLimiteCredito(), request.getLimiteCredito()));
        return cartaoCreditoRepository.save(cartaoCredito);
    }

    private boolean numeroCartaoJaRegistrado(String numeroCartao, Long idCartao) {
        Optional<CartaoCredito> optional = cartaoCreditoRepository.findByNumero(numeroCartao);
        return optional.filter(cartaoCredito -> !cartaoCredito.getIdCartaoCredito().equals(idCartao)).isPresent();
    }

    public void remover(Long id, ContaBancaria contaBancaria) {
        CartaoCredito cartaoCredito = buscarPorId(id, contaBancaria);
        cartaoCreditoRepository.delete(cartaoCredito);
    }

    public ResumoItemCartaoResponse resumoItensCartao(Long idCartao, FiltroPaginacao filtro, ContaBancaria contaBancaria) {
        CartaoCredito cartaoCredito = buscarPorId(idCartao, contaBancaria);

        List<ItemCartao> itens = cartaoCredito.getItens().stream()
                .map(item -> parcelasComDataRequisitada(item, filtro.getMes(), filtro.getAno()))
                .filter(item -> item.getParcelas().size() > 0)
                .collect(Collectors.toList());

        cartaoCredito.setItens(itens);
        return cartaoCreditoMapper.toResumoItemCartaoResponse(cartaoCredito);
    }

    private ItemCartao parcelasComDataRequisitada(ItemCartao item, Integer mes, Integer ano) {
        List<Parcela> parcelas = item.getParcelas().stream()
                .filter(parcela -> dataVencimentoParcelaIgualDataRequisitada(parcela, mes, ano))
                .collect(Collectors.toList());
        item.setParcelas(parcelas);
        return item;
    }

    private boolean dataVencimentoParcelaIgualDataRequisitada(Parcela parcela, Integer mes, Integer ano) {
        return parcela.getDataVencimento().getMonthValue() == mes &&
                parcela.getDataVencimento().getYear() == ano;
    }

    public void adicionarItem(Long idCartao, ItemCartaoRequest request, ContaBancaria contaBancaria) {
        CartaoCredito cartaoCredito =
                cartaoCreditoRepository.findByIdCartaoCreditoAndContaBancaria(idCartao, contaBancaria)
                        .orElseThrow(() -> new ResourceNotFoundException("Cartão de crédito não encontrado"));

        ItemCartao itemCartao = itemCartaoMapper.toItemCartao(request, cartaoCredito.getDiaVencimento());
        itemCartao.setContaBancaria(contaBancaria);
        cartaoCredito.adicionarItem(itemCartao);
        cartaoCreditoRepository.save(cartaoCredito);
    }

    public ItemCartao buscarItemPorId(Long id, ContaBancaria contaBancaria) {
        return itemCartaoRepository.findByIdItemCartaoAndContaBancaria(id, contaBancaria)
                .orElseThrow(() -> new ResourceNotFoundException("Item do cartão de crédito não encontrado"));
    }

    public CartaoCredito atualizarItem(Long idCartao,
                                       Long idItem,
                                       ItemCartaoRequest request,
                                       ContaBancaria contaBancaria) {
        CartaoCredito cartaoCredito = buscarPorId(idCartao, contaBancaria);
        ItemCartao itemAtual = buscarItemNoCartao(idItem, cartaoCredito);

        ItemCartaoRequest validatedRequest = validarItemCartao(request, itemAtual);
        ItemCartao itemAtualizado = itemCartaoMapper.toItemCartao(validatedRequest, cartaoCredito.getDiaVencimento());
        itemAtualizado.setIdItemCartao(itemAtual.getIdItemCartao());
        itemAtualizado.setContaBancaria(contaBancaria);
        cartaoCredito.atualizarItemCartao(itemAtual, itemAtualizado);

        return cartaoCreditoRepository.save(cartaoCredito);
    }

    private ItemCartao buscarItemNoCartao(Long idItemCartao, CartaoCredito cartaoCredito) {
        return cartaoCredito.getItens().stream()
                .filter(cartao -> cartao.getIdItemCartao().equals(idItemCartao))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado no cartão de crédito"));
    }

    private ItemCartaoRequest validarItemCartao(ItemCartaoRequest request, ItemCartao itemAtual) {
        String dataCompraItemAtual = DateHelper.formatDate(itemAtual.getDataCompra(), "dd/MM/yyyy");
        String dataCompra = validaTexto(dataCompraItemAtual, request.getDataCompra());
        String descricao = validaTexto(itemAtual.getDescricao(), request.getDescricao());
        BigDecimal valorTotal = validaNumero(itemAtual.getValorTotal(), request.getValorTotal());
        Integer numeroParcelas = validaNumero(itemAtual.getNumeroParcelas(), request.getNumeroParcelas());

        return new ItemCartaoRequest(dataCompra, descricao, valorTotal, numeroParcelas);
    }

    public void removerItemPorId(Long id, ContaBancaria contaBancaria) {
        ItemCartao item = itemCartaoRepository.findByIdItemCartaoAndContaBancaria(id, contaBancaria)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado"));
        itemCartaoRepository.delete(item);
    }
}
