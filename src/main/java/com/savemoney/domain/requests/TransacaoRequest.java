package com.savemoney.domain.requests;

import com.savemoney.domain.enums.TipoTransacao;
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
public class TransacaoRequest {

    @Positive
    private BigDecimal valor;

    @NotNull
    private String descricao;

    @NotNull
    private TipoTransacao tipoTransacao;
}
