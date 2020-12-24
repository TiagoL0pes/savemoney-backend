package com.savemoney.domain.models;

import com.savemoney.domain.enums.StatusPagamento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "faturas")
public class Fatura implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fatura")
    private Long idFatura;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_fatura")
    private List<Parcela> parcelas = new ArrayList<>();

    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_pagamento")
    private StatusPagamento statusPagamento;

    @Column(name = "total", precision = 14, scale = 5)
    private BigDecimal total;

    @ManyToOne
    @JoinColumn(name = "fk_cartao_credito")
    private CartaoCredito cartaoCredito;

    public Fatura(List<Parcela> parcelas,
                  StatusPagamento statusPagamento,
                  LocalDate dataVencimento) {
        this.parcelas = parcelas;
        this.statusPagamento = statusPagamento;
        this.dataVencimento = dataVencimento;
        this.total = getTotalFatura();
    }

    private BigDecimal getTotalFatura() {
        return parcelas.stream()
                .map(Parcela::getValor)
                .reduce(BigDecimal::add)
                .get().setScale(5, RoundingMode.HALF_EVEN);
    }
}
