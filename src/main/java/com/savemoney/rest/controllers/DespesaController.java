package com.savemoney.rest.controllers;

import com.savemoney.abstracts.AbstractController;
import com.savemoney.domain.models.Despesa;
import com.savemoney.domain.pagination.DespesasPagination;
import com.savemoney.domain.requests.DespesaRequest;
import com.savemoney.domain.responses.DespesaResponse;
import com.savemoney.rest.facades.DespesaFacade;
import com.savemoney.rest.filters.FiltroPaginacao;
import com.savemoney.rest.swagger.DespesaSwagger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/despesas")
public class DespesaController extends AbstractController implements DespesaSwagger {

    @Autowired
    private DespesaFacade despesaFacade;

    @PostMapping
    public ResponseEntity<Void> adicionar(@RequestHeader("Authorization") String token,
                                          @RequestBody DespesaRequest request) {
        Despesa despesa = despesaFacade.adicionar(token, request);

        URI uri = montarURIPara("/{id}", despesa.getIdDespesa());

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<DespesaResponse> buscarPorId(@RequestHeader("Authorization") String token,
                                                       @PathVariable Long id) {
        DespesaResponse response = despesaFacade.buscarPorId(token, id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<DespesasPagination> listar(@RequestHeader("Authorization") String token,
                                                     @RequestParam(name = "mes", required = false) Integer mes,
                                                     @RequestParam(name = "ano", required = false) Integer ano,
                                                     @PageableDefault(sort = "descricao", direction = Sort.Direction.ASC) Pageable pageable) {
        FiltroPaginacao filtro = new FiltroPaginacao(mes, ano);
        DespesasPagination pagination = despesaFacade.listar(token, filtro, pageable);
        return ResponseEntity.ok(pagination);
    }

    @PutMapping("{id}")
    public ResponseEntity<DespesaResponse> atualizar(@RequestHeader("Authorization") String token,
                                                     @PathVariable Long id,
                                                     @RequestBody DespesaRequest request) {
        DespesaResponse response = despesaFacade.atualizar(token, id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> remover(@RequestHeader("Authorization") String token,
                                        @PathVariable Long id) {
        despesaFacade.remover(token, id);
        return ResponseEntity.noContent().build();
    }
}
