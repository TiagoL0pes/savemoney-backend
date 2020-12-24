package com.savemoney.rest.controllers;

import com.savemoney.abstracts.AbstractController;
import com.savemoney.domain.models.CartaoCredito;
import com.savemoney.domain.pagination.CartoesCreditoPagination;
import com.savemoney.domain.requests.CartaoCreditoRequest;
import com.savemoney.domain.requests.ItemCartaoRequest;
import com.savemoney.domain.responses.CartaoCreditoResponse;
import com.savemoney.domain.responses.ItemCartaoResponse;
import com.savemoney.domain.responses.ResumoItemCartaoResponse;
import com.savemoney.rest.facades.CartaoCreditoFacade;
import com.savemoney.rest.swagger.CartaoCreditoSwagger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/cartoes/credito")
public class CartaoCreditoController extends AbstractController implements CartaoCreditoSwagger {

    @Autowired
    private CartaoCreditoFacade cartaoCreditoFacade;

    @PostMapping
    public ResponseEntity<Void> adicionar(@RequestHeader("Authorization") String token,
                                          @RequestBody CartaoCreditoRequest request) {
        CartaoCredito cartaoCredito = cartaoCreditoFacade.adicionar(token, request);

        URI uri = montarURIPara("/{id}", cartaoCredito.getIdCartaoCredito());

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<CartaoCreditoResponse> buscarPorId(@RequestHeader("Authorization") String token,
                                                             @PathVariable Long id) {
        CartaoCreditoResponse response = cartaoCreditoFacade.buscarPorId(token, id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<CartoesCreditoPagination> listar(@RequestHeader("Authorization") String token,
                                                           @PageableDefault(sort = "numero", direction = Sort.Direction.ASC) Pageable pageable) {
        CartoesCreditoPagination pagination = cartaoCreditoFacade.listar(token, pageable);
        return ResponseEntity.ok(pagination);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar(@RequestHeader("Authorization") String token,
                                          @PathVariable Long id,
                                          @RequestBody CartaoCreditoRequest request) {
        cartaoCreditoFacade.atualizar(token, id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> remover(@RequestHeader("Authorization") String token,
                                        @PathVariable Long id) {
        cartaoCreditoFacade.remover(token, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{idCartao}/resumo/itens")
    public ResponseEntity<ResumoItemCartaoResponse> resumoItensCartao(@RequestHeader("Authorization") String token,
                                                                      @PathVariable Long idCartao,
                                                                      @RequestParam(name = "mes", required = false) Integer mes,
                                                                      @RequestParam(name = "ano", required = false) Integer ano) {
        ResumoItemCartaoResponse response = cartaoCreditoFacade.resumoItensCartao(token, idCartao, mes, ano);
        return ResponseEntity.ok(response);
    }


    @PostMapping("{idCartaoCredito}/adicionar/itens")
    public ResponseEntity<Void> adicionarItem(@RequestHeader("Authorization") String token,
                                              @PathVariable Long idCartaoCredito,
                                              @RequestBody ItemCartaoRequest request) {
        cartaoCreditoFacade.adicionarItem(token, idCartaoCredito, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("items/{idItem}")
    public ResponseEntity<ItemCartaoResponse> buscarItemPorId(@RequestHeader("Authorization") String token,
                                                              @PathVariable Long idItem) {
        ItemCartaoResponse response = cartaoCreditoFacade.buscarItemPorId(token, idItem);
        return ResponseEntity.ok(response);
    }

    @PutMapping("{idCartaoCredito}/itens/{idItem}")
    public ResponseEntity<Void> atualizarItem(@RequestHeader("Authorization") String token,
                                              @PathVariable Long idCartaoCredito,
                                              @PathVariable Long idItem,
                                              @RequestBody ItemCartaoRequest request) {
        cartaoCreditoFacade.atualizarItem(token, idCartaoCredito, idItem, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{idCartao}/itens")
    public ResponseEntity<Void> removerItemPorId(@RequestHeader("Authorization") String token,
                                                 @PathVariable Long idCartao) {
        cartaoCreditoFacade.removerItemPorId(token, idCartao);
        return ResponseEntity.noContent().build();
    }

}
