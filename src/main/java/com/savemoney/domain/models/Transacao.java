package com.savemoney.domain.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.savemoney.domain.enums.TipoTransacao;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "transacoes")
public class Transacao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transacao")
    private Long idTransacao;

    @Column(name = "data_entrada")
    private LocalDateTime dataEntrada;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "valor", precision = 14, scale = 5)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_transacao")
    private TipoTransacao tipoTransacao;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "fk_conta_bancaria")
    private ContaBancaria contaBancaria;

    public Transacao(Long idTransacao,
                     LocalDateTime dataEntrada,
                     String descricao,
                     BigDecimal valor,
                     TipoTransacao tipoTransacao) {
        this.idTransacao = idTransacao;
        this.dataEntrada = dataEntrada;
        this.descricao = descricao;
        this.valor = valor;
        this.tipoTransacao = tipoTransacao;
    }
}
