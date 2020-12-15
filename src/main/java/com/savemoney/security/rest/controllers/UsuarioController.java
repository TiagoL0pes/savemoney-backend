package com.savemoney.security.rest.controllers;

import com.savemoney.abstracts.AbstractController;
import com.savemoney.domain.responses.UsuarioResponse;
import com.savemoney.security.domain.models.Usuario;
import com.savemoney.security.domain.requests.UsuarioRequest;
import com.savemoney.security.rest.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController extends AbstractController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Void> adicionar(@Valid @RequestBody UsuarioRequest request) {
        Usuario usuario = usuarioService.adicionar(request);

        URI uri = montarURIPara("/{id}", usuario.getIdUsuario());

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Long id) {
        UsuarioResponse usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }
}
