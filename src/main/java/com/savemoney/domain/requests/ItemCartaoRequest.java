package com.savemoney.domain.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemCartaoRequest {

    @NotBlank
    private String dataCompra;

    @NotNull
    private String descricao;

    @Positive
    private BigDecimal valorTotal;

    @Positive
    private Integer numeroParcelas;
}
