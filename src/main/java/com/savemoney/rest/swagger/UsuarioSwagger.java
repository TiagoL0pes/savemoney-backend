package com.savemoney.rest.swagger;

import com.savemoney.domain.responses.UsuarioResponse;
import com.savemoney.security.domain.requests.UsuarioRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

@Api(description = "Controlador de usuários", tags = {"Usuário Controller"})
public interface UsuarioSwagger {

    @ApiOperation("Adicionar novo usuário")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar esse recurso"),
            @ApiResponse(code = 400, message = "Requisição inválida, verifique as informações enviadas"),
            @ApiResponse(code = 404, message = "Recurso não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno de servidor"),
    })
    ResponseEntity<Void> adicionar(UsuarioRequest request);

    @ApiOperation("Buscar usuário por id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar esse recurso"),
            @ApiResponse(code = 400, message = "Requisição inválida, verifique as informações enviadas"),
            @ApiResponse(code = 404, message = "Recurso não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno de servidor"),
    })
    ResponseEntity<UsuarioResponse> buscarPorId(Long id);
}
