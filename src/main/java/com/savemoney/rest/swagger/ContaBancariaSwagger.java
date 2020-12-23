package com.savemoney.rest.swagger;

import com.savemoney.domain.pagination.ContasBancariasPagination;
import com.savemoney.domain.requests.ContaBancariaRequest;
import com.savemoney.domain.requests.TransacaoRequest;
import com.savemoney.domain.responses.ContaBancariaResponse;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import springfox.documentation.annotations.ApiIgnore;

@Api(description = "Controlador de contas bancárias", tags = {"Conta Bancária Controller"})
public interface ContaBancariaSwagger {

    @ApiOperation("Adicionar nova conta bancária")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar esse recurso"),
            @ApiResponse(code = 400, message = "Requisição inválida, verifique as informações enviadas"),
            @ApiResponse(code = 404, message = "Recurso não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno de servidor"),
    })
    ResponseEntity<Void> adicionar(ContaBancariaRequest request);

    @ApiOperation("Buscar conta bancária por id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar esse recurso"),
            @ApiResponse(code = 400, message = "Requisição inválida, verifique as informações enviadas"),
            @ApiResponse(code = 404, message = "Recurso não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno de servidor"),
    })
    ResponseEntity<ContaBancariaResponse> buscarPorId(Long id);

    @ApiOperation("Buscar lista paginada de contas bancárias")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "int", paramType = "query",
                    value = "Página de resultados que você deseja recuperar (0..N)", defaultValue = "0", example = "2"),
            @ApiImplicitParam(name = "size", dataType = "int", paramType = "query",
                    value = "Número de registros por página.", defaultValue = "20", example = "10"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Critérios de classificação no formato: propriedade(asc|desc). "
                            + "A ordem de classificação padrão é ascendente (asc). " + "Suporte a múltiplos criterios de ordenação.")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar esse recurso"),
            @ApiResponse(code = 400, message = "Requisição inválida, verifique as informações enviadas"),
            @ApiResponse(code = 404, message = "Recurso não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno de servidor"),
    })
    ResponseEntity<ContasBancariasPagination> listar(@ApiIgnore Pageable pageable);

    @ApiOperation("Atualizar conta bancária existente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar esse recurso"),
            @ApiResponse(code = 400, message = "Requisição inválida, verifique as informações enviadas"),
            @ApiResponse(code = 404, message = "Recurso não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno de servidor"),
    })
    ResponseEntity<Void> atualizar(Long id, ContaBancariaRequest request);

    @ApiOperation("Remover conta bancária existente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar esse recurso"),
            @ApiResponse(code = 400, message = "Requisição inválida, verifique as informações enviadas"),
            @ApiResponse(code = 404, message = "Recurso não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno de servidor"),
    })
    ResponseEntity<Void> remover(Long id);

    @ApiOperation("Depositar um valor no saldo da conta bancária")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar esse recurso"),
            @ApiResponse(code = 400, message = "Requisição inválida, verifique as informações enviadas"),
            @ApiResponse(code = 404, message = "Recurso não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno de servidor"),
    })
    ResponseEntity<Void> depositar(Long id, TransacaoRequest request);

    @ApiOperation("Sacar um valor do saldo da conta bancária")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar esse recurso"),
            @ApiResponse(code = 400, message = "Requisição inválida, verifique as informações enviadas"),
            @ApiResponse(code = 404, message = "Recurso não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno de servidor"),
    })
    ResponseEntity<Void> sacar(Long id, TransacaoRequest request);
}
