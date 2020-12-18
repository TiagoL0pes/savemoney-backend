package com.savemoney.domain.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DespesaRequest {

    private String dataEntrada;

    private String dataVencimento;

    @NotNull
    private String descricao;

    @Positive
    private BigDecimal valor;
}
