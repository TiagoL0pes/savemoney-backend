package com.savemoney.rest.controllers;

import com.savemoney.rest.facades.PagamentoFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoFacade pagamentoFacade;

    @PostMapping("/despesa/{id}")
    public ResponseEntity<Void> pagarDespesa(@RequestHeader("Authorization") String token,
                                             @PathVariable Long id) {
        pagamentoFacade.pagarDespesa(token, id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/fatura/{id}")
    public ResponseEntity<Void> pagarFatura(@RequestHeader("Authorization") String token,
                                            @PathVariable Long id) {
        pagamentoFacade.pagarFatura(token, id);
        return ResponseEntity.ok().build();
    }
}
