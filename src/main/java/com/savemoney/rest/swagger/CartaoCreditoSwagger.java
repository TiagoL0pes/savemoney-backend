package com.savemoney.rest.swagger;

import com.savemoney.domain.pagination.CartoesCreditoPagination;
import com.savemoney.domain.requests.CartaoCreditoRequest;
import com.savemoney.domain.requests.ItemCartaoRequest;
import com.savemoney.domain.responses.CartaoCreditoResponse;
import com.savemoney.domain.responses.ItemCartaoResponse;
import com.savemoney.domain.responses.ResumoItemCartaoResponse;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import springfox.documentation.annotations.ApiIgnore;

@Api(description = "Controlador de cartões de crédito", tags = {"Cartão de Crédito Controller"})
public interface CartaoCreditoSwagger {

    @ApiOperation("Adicionar novo cartão de crédito")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar esse recurso"),
            @ApiResponse(code = 400, message = "Requisição inválida, verifique as informações enviadas"),
            @ApiResponse(code = 404, message = "Recurso não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno de servidor"),
    })
    ResponseEntity<Void> adicionar(@ApiIgnore String token, CartaoCreditoRequest request);

    @ApiOperation("Buscar cartão de crédito por id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar esse recurso"),
            @ApiResponse(code = 400, message = "Requisição inválida, verifique as informações enviadas"),
            @ApiResponse(code = 404, message = "Recurso não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno de servidor"),
    })
    ResponseEntity<CartaoCreditoResponse> buscarPorId(@ApiIgnore String token, Long id);

    @ApiOperation("Buscar lista paginada de cartões de crédito")
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
    ResponseEntity<CartoesCreditoPagination> listar(@ApiIgnore String token, @ApiIgnore Pageable pageable);

    @ApiOperation("Atualizar cartão de crédito existente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar esse recurso"),
            @ApiResponse(code = 400, message = "Requisição inválida, verifique as informações enviadas"),
            @ApiResponse(code = 404, message = "Recurso não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno de servidor"),
    })
    ResponseEntity<Void> atualizar(@ApiIgnore String token, Long id, CartaoCreditoRequest request);

    @ApiOperation("Remover cartão de crédito existente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar esse recurso"),
            @ApiResponse(code = 400, message = "Requisição inválida, verifique as informações enviadas"),
            @ApiResponse(code = 404, message = "Recurso não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno de servidor"),
    })
    ResponseEntity<Void> remover(@ApiIgnore String token, Long id);

    @ApiOperation("Retornar resumo de itens do cartão de crédito filtrados por mês e ano")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar esse recurso"),
            @ApiResponse(code = 400, message = "Requisição inválida, verifique as informações enviadas"),
            @ApiResponse(code = 404, message = "Recurso não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno de servidor"),
    })
    ResponseEntity<ResumoItemCartaoResponse> resumoItensCartao(@ApiIgnore String token, Long id, Integer mes, Integer ano);

    @ApiOperation("Adicionar novo item no cartão de crédito")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar esse recurso"),
            @ApiResponse(code = 400, message = "Requisição inválida, verifique as informações enviadas"),
            @ApiResponse(code = 404, message = "Recurso não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno de servidor"),
    })
    ResponseEntity<Void> adicionarItem(@ApiIgnore String token, Long creditCardId, ItemCartaoRequest request);

    @ApiOperation("Buscar item do cartão por id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar esse recurso"),
            @ApiResponse(code = 400, message = "Requisição inválida, verifique as informações enviadas"),
            @ApiResponse(code = 404, message = "Recurso não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno de servidor"),
    })
    ResponseEntity<ItemCartaoResponse> buscarItemPorId(@ApiIgnore String token, Long id);

    @ApiOperation("Atualizar item do cartão")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar esse recurso"),
            @ApiResponse(code = 400, message = "Requisição inválida, verifique as informações enviadas"),
            @ApiResponse(code = 404, message = "Recurso não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno de servidor"),
    })
    ResponseEntity<Void> atualizarItem(@ApiIgnore String token, Long creditCardId, Long cardItemId, ItemCartaoRequest request);

    @ApiOperation("Remover item do cartão por id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar esse recurso"),
            @ApiResponse(code = 400, message = "Requisição inválida, verifique as informações enviadas"),
            @ApiResponse(code = 404, message = "Recurso não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno de servidor"),
    })
    ResponseEntity<Void> removerItemPorId(@ApiIgnore String token, Long id);
}
