package com.savemoney.domain.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContaBancariaResponse {

    private Long idContaBancaria;

    private String agencia;

    private String conta;

    private BigDecimal saldo;

    private BancoResponse banco;
}
