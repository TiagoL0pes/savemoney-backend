package com.savemoney.domain.responses;

import com.savemoney.domain.enums.StatusPagamento;
import com.savemoney.domain.enums.TipoTransacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.savemoney.domain.enums.TipoTransacao.SAIDA;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DespesaResponse {

    private Long idDespesa;

    private LocalDate dataEntrada;

    private LocalDate dataPagamento;

    private LocalDate dataVencimento;

    private String descricao;

    private BigDecimal valor;

    private StatusPagamento statusPagamento;

    private TipoTransacao tipoTransacao = SAIDA;
}
