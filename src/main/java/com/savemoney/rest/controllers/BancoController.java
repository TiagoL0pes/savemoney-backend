package com.savemoney.rest.controllers;

import com.savemoney.domain.pagination.BancosPagination;
import com.savemoney.rest.services.BancoService;
import com.savemoney.rest.swagger.BancoSwagger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bancos")
public class BancoController implements BancoSwagger {

    @Autowired
    private BancoService bancoService;

    @GetMapping
    public ResponseEntity<BancosPagination> listar(
            @PageableDefault(sort = "codigo", direction = Sort.Direction.ASC) Pageable pageable) {
        BancosPagination pagination = bancoService.listar(pageable);
        return ResponseEntity.ok(pagination);
    }
}
