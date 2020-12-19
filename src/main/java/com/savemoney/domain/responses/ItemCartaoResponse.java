package com.savemoney.domain.responses;

import com.savemoney.domain.models.Parcela;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemCartaoResponse {

    private Long idItemCartao;

    private LocalDate dataCompra;

    private String descricao;

    private BigDecimal valor;

    private Integer numeroParcelas;

    private List<Parcela> parcelas = new ArrayList<>();
}
