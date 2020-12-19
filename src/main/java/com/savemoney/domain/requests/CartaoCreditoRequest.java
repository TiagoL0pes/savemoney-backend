package com.savemoney.domain.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartaoCreditoRequest {

    private Long idCartaoCredito;

    private String numero;

    private Integer diaVencimento;

    private BigDecimal limiteCredito;

    private ContaBancariaRequest contaBancaria;
}
