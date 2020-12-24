package com.savemoney.domain.responses;

import com.savemoney.domain.enums.StatusPagamento;
import com.savemoney.domain.models.Parcela;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FaturaResponse {

    private Long idFatura;

    private String dataVencimento;

    private StatusPagamento statusPagamento;

    private BigDecimal total;

    private List<ParcelaResponse> parcelas;
}
