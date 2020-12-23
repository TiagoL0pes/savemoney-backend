package com.savemoney.rest.swagger;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import springfox.documentation.annotations.ApiIgnore;

@Api(description = "Controlador de pagamentos", tags = {"Pagamento Controller"})
public interface PagamentoSwagger {

    @ApiOperation("Pagar despesa")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar esse recurso"),
            @ApiResponse(code = 400, message = "Requisição inválida, verifique as informações enviadas"),
            @ApiResponse(code = 404, message = "Recurso não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno de servidor"),
    })
    ResponseEntity<Void> pagarDespesa(@ApiIgnore String token, Long id);

    @ApiOperation("Pagar fatura")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar esse recurso"),
            @ApiResponse(code = 400, message = "Requisição inválida, verifique as informações enviadas"),
            @ApiResponse(code = 404, message = "Recurso não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno de servidor"),
    })
    ResponseEntity<Void> pagarFatura(@ApiIgnore String token, Long id);
}
