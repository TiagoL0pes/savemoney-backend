package com.savemoney.security.rest.controllers;

import com.savemoney.domain.models.ContaBancaria;
import com.savemoney.rest.swagger.AuthSwagger;
import com.savemoney.security.domain.models.Usuario;
import com.savemoney.security.domain.requests.AuthRequest;
import com.savemoney.security.domain.responses.TokenPayloadResponse;
import com.savemoney.security.domain.responses.TokenResponse;
import com.savemoney.security.rest.services.UserDetailsServiceImpl;
import com.savemoney.security.utils.JSONUtil;
import com.savemoney.security.utils.JwtUtil;
import com.savemoney.utils.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController implements AuthSwagger {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JSONUtil jsonUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping
    public ResponseEntity<TokenResponse> auth(@Valid @RequestBody AuthRequest request) {
        UsernamePasswordAuthenticationToken login = request.generateCredencials(Collections.emptyList());
        try {
            Authentication authentication = authenticationManager.authenticate(login);
            Usuario usuario = userDetailsService.findByEmail(request.getEmail());

            ContaBancaria contaBancaria = usuario.getContaBancaria();
            TokenPayloadResponse tokenPayload = TokenPayloadResponse.builder()
                    .idContaBancaria(contaBancaria.getIdContaBancaria())
                    .email(authentication.getName())
                    .agencia(contaBancaria.getAgencia())
                    .conta(contaBancaria.getConta())
                    .build();

            String payload = jsonUtil.toJson(tokenPayload);
            String token = jwtUtil.generateToken(payload);

            return ResponseEntity.ok(new TokenResponse(token));
        } catch (AuthenticationException e) {
            throw new BadRequestException("Usuário ou senha inválidos");
        }
    }
}
