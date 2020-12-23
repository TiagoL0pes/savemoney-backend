package com.savemoney.rest.swagger;

import com.savemoney.domain.pagination.BancosPagination;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import springfox.documentation.annotations.ApiIgnore;

@Api(description = "Controlador de bancos", tags = {"Banco Controller"})
public interface BancoSwagger {

    @ApiOperation("Buscar lista paginada de bancos")
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
    ResponseEntity<BancosPagination> listar(@ApiIgnore Pageable pageable);
}
