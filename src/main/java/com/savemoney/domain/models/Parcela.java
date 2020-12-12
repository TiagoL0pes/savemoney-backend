package com.savemoney.domain.models;

import com.savemoney.domain.enums.Mes;
import com.savemoney.domain.enums.StatusPagamento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "parcelas")
public class Parcela implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_parcela")
    private Long idParcela;

    @Column(name = "numero_parcela", updatable = false)
    private Integer numeroParcela;

    @Column(name = "valor", precision = 14, scale = 5)
    private BigDecimal valor;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;

    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "mes_vencimento")
    private Mes mesVencimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusPagamento statusPagamento;

    public Parcela(Long idParcela,
                       Integer numeroParcela,
                       BigDecimal valor,
                       StatusPagamento status,
                       LocalDate dataVencimento) {
        this.idParcela = idParcela;
        this.numeroParcela = numeroParcela;
        this.valor = valor;
        this.statusPagamento = status;
        this.dataVencimento = dataVencimento;
    }
}
