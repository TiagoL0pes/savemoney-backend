package com.savemoney.rest.controllers;

import com.savemoney.abstracts.AbstractController;
import com.savemoney.domain.mappers.FaturaMapper;
import com.savemoney.domain.models.Fatura;
import com.savemoney.domain.pagination.FaturasPagination;
import com.savemoney.domain.requests.FaturaRequest;
import com.savemoney.domain.responses.FaturaResponse;
import com.savemoney.rest.facades.FaturaFacade;
import com.savemoney.rest.swagger.FaturaSwagger;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("faturas")
public class FaturaController extends AbstractController implements FaturaSwagger {

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
    public ResponseEntity<FaturaResponse> buscarPorId(@PathVariable Long idFatura) {
        FaturaResponse response = faturaFacade.buscarPorId(idFatura);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<FaturasPagination> listar(
            @RequestParam(name = "idCartao") Long idCartao,
            @PageableDefault(sort = "dataVencimento", direction = Sort.Direction.DESC) Pageable pageable) {
        FaturasPagination pagination = faturaFacade.listar(idCartao, pageable);
        return ResponseEntity.ok(pagination);
    }
}
