package com.savemoney.rest.swagger;

import com.savemoney.domain.pagination.DespesasPagination;
import com.savemoney.domain.requests.DespesaRequest;
import com.savemoney.domain.responses.DespesaResponse;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import springfox.documentation.annotations.ApiIgnore;

@Api(description = "Controlador de despesas", tags = {"Despesa Controller"})
public interface DespesaSwagger {

    @ApiOperation("Adicionar nova despesa")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar esse recurso"),
            @ApiResponse(code = 400, message = "Requisição inválida, verifique as informações enviadas"),
            @ApiResponse(code = 404, message = "Recurso não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno de servidor"),
    })
    ResponseEntity<Void> adicionar(@ApiIgnore String token, DespesaRequest request);


    @ApiOperation("Buscar despesa por id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar esse recurso"),
            @ApiResponse(code = 400, message = "Requisição inválida, verifique as informações enviadas"),
            @ApiResponse(code = 404, message = "Recurso não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno de servidor"),
    })
    ResponseEntity<DespesaResponse> buscarPorId(@ApiIgnore String token, Long id);

    @ApiOperation("Buscar lista paginada de despesas")
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
    ResponseEntity<DespesasPagination> listar(@ApiIgnore String token, Integer mes, Integer ano, @ApiIgnore Pageable pageable);

    @ApiOperation("Atualizar despesa existente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar esse recurso"),
            @ApiResponse(code = 400, message = "Requisição inválida, verifique as informações enviadas"),
            @ApiResponse(code = 404, message = "Recurso não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno de servidor"),
    })
    ResponseEntity<DespesaResponse> atualizar(@ApiIgnore String token, Long id, DespesaRequest request);

    @ApiOperation("Remover despesa existente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar esse recurso"),
            @ApiResponse(code = 400, message = "Requisição inválida, verifique as informações enviadas"),
            @ApiResponse(code = 404, message = "Recurso não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno de servidor"),
    })
    ResponseEntity<Void> remover(@ApiIgnore String token, Long id);
}
