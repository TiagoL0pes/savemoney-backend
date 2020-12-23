package com.savemoney.rest.swagger;

import com.savemoney.security.domain.requests.AuthRequest;
import com.savemoney.security.domain.responses.TokenResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

@Api(description = "Controlador de autenticação", tags = {"Auth Controller"})
public interface AuthSwagger {

    @ApiOperation("Autenticar usuário")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar esse recurso"),
            @ApiResponse(code = 400, message = "Requisição inválida, verifique as informações enviadas"),
            @ApiResponse(code = 404, message = "Recurso não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno de servidor"),
    })
    ResponseEntity<TokenResponse> auth(AuthRequest request);
}
