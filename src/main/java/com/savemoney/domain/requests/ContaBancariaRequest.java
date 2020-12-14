package com.savemoney.domain.requests;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContaBancariaRequest {

    @Positive
    private Long idBanco;

    @NotEmpty
    private String agencia;

    @NotEmpty
    private String conta;

    @NonNull
    private BigDecimal saldo;
}
