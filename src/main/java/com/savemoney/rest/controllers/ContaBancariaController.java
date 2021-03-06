package com.savemoney.rest.controllers;

import com.savemoney.abstracts.AbstractController;
import com.savemoney.domain.models.ContaBancaria;
import com.savemoney.domain.pagination.ContasBancariasPagination;
import com.savemoney.domain.requests.ContaBancariaRequest;
import com.savemoney.domain.requests.TransacaoRequest;
import com.savemoney.domain.responses.ContaBancariaResponse;
import com.savemoney.rest.facades.ContaBancariaFacade;
import com.savemoney.rest.swagger.ContaBancariaSwagger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/contas/bancarias")
public class ContaBancariaController extends AbstractController implements ContaBancariaSwagger {

    @Autowired
    private ContaBancariaFacade contaBancariaFacade;

    @PostMapping
    public ResponseEntity<Void> adicionar(@RequestHeader("Authorization") String token,
                                          @Valid @RequestBody ContaBancariaRequest request) {
        ContaBancaria contaBancaria = contaBancariaFacade.adicionar(token, request);

        URI uri = montarURIPara("/{id}", contaBancaria.getIdContaBancaria());

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ContaBancariaResponse> buscarPorId(@PathVariable Long id) {
        ContaBancariaResponse response = contaBancariaFacade.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ContasBancariasPagination> listar(
            @PageableDefault(sort = "idContaBancaria", direction = Sort.Direction.ASC) Pageable pageable) {
        ContasBancariasPagination pagination = contaBancariaFacade.listar(pageable);
        return ResponseEntity.ok(pagination);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody ContaBancariaRequest request) {
        contaBancariaFacade.atualizar(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        contaBancariaFacade.remover(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/depositar/{id}")
    public ResponseEntity<Void> depositar(@PathVariable Long id, @RequestBody TransacaoRequest request) {
        contaBancariaFacade.realizarTransacao(id, request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/sacar/{id}")
    public ResponseEntity<Void> sacar(@PathVariable Long id, @RequestBody TransacaoRequest request) {
        contaBancariaFacade.realizarTransacao(id, request);
        return ResponseEntity.ok().build();
    }
}
