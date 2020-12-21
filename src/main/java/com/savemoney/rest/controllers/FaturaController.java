package com.savemoney.rest.controllers;

import com.savemoney.abstracts.AbstractController;
import com.savemoney.domain.models.Fatura;
import com.savemoney.domain.pagination.FaturasPagination;
import com.savemoney.domain.requests.FaturaRequest;
import com.savemoney.rest.facades.FaturaFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("faturas")
public class FaturaController extends AbstractController {

    @Autowired
    private FaturaFacade faturaFacade;

    @PostMapping
    public ResponseEntity<Void> gerar(@RequestBody FaturaRequest request) {
        Fatura fatura = faturaFacade.gerarFatura(request);

        URI uri = montarURIPara("/{id}", fatura.getIdFatura());

        return ResponseEntity.created(uri).build();
    }

    @PostMapping("{idFatura}")
    public ResponseEntity<Void> atualizar(@PathVariable Long idFatura, @RequestBody FaturaRequest request) {
        faturaFacade.atualizarFatura(idFatura, request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{idFatura}")
    public ResponseEntity<Fatura> buscarPorId(@PathVariable Long idFatura) {
        Fatura fatura = faturaFacade.buscarPorId(idFatura);
        return ResponseEntity.ok(fatura);
    }

    @GetMapping
    public ResponseEntity<FaturasPagination> listar(
            @RequestParam(name = "idCartao") Long idCartao,
            @PageableDefault(sort = "dataVencimento", direction = Sort.Direction.DESC) Pageable pageable) {
        FaturasPagination pagination = faturaFacade.listar(idCartao, pageable);
        return ResponseEntity.ok(pagination);
    }
}
