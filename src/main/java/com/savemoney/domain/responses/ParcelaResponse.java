package com.savemoney.domain.responses;

import com.savemoney.domain.enums.Mes;
import com.savemoney.domain.enums.StatusPagamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParcelaResponse {

    private Long idParcela;

    private Integer numeroParcela;

    private BigDecimal valor;

    private String descricao;

    private LocalDate dataPagamento;

    private LocalDate dataVencimento;

    private Mes mesVencimento;

    private StatusPagamento statusPagamento;
}
