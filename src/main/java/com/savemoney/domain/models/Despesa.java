package com.savemoney.domain.models;

import com.savemoney.domain.enums.StatusPagamento;
import com.savemoney.domain.enums.TipoTransacao;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import static com.savemoney.domain.enums.TipoTransacao.SAIDA;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "despesas")
public class Despesa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_despesa")
    private Long idDespesa;

    @Column(name = "data_entrada")
    private LocalDate dataEntrada;

    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;

    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "valor", precision = 14, scale = 5)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_pagamento")
    private StatusPagamento statusPagamento;

    @OneToOne
    @JoinColumn(name = "fk_conta_bancaria")
    private ContaBancaria contaBancaria;

    @Transient
    private TipoTransacao tipoTransacao = SAIDA;

    public Despesa(LocalDate dataEntrada,
                   LocalDate dataVencimento,
                   String descricao,
                   BigDecimal valor,
                   StatusPagamento statusPagamento) {
        this.dataEntrada = dataEntrada;
        this.dataVencimento = dataVencimento;
        this.descricao = descricao;
        this.valor = valor;
        this.statusPagamento = statusPagamento;
    }
}
