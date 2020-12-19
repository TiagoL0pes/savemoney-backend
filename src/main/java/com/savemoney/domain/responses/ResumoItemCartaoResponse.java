package com.savemoney.domain.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResumoItemCartaoResponse {

    private Long idCartaoCredito;

    private Integer totalItens;

    private BigDecimal valorTotal;

    private List<ItemCartaoResponse> itens = new ArrayList<>();
}
