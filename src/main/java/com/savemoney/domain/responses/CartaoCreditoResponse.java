package com.savemoney.domain.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartaoCreditoResponse {

    private Long idCartaoCredito;

    private String numero;

    private Integer dataVencimento;

    private BigDecimal limiteCredito;

    private BigDecimal limiteUtilizado;

    @JsonProperty("limiteDisponivel")
    private BigDecimal limiteDisponivel() {
        BigDecimal usedLimit = Objects.nonNull(this.limiteUtilizado) ?
                this.limiteUtilizado : BigDecimal.ZERO;
        return this.limiteCredito.subtract(usedLimit);
    }
}
