package com.savemoney.domain.models;

import com.savemoney.security.domain.models.Usuario;
import com.savemoney.utils.exceptions.TransactionNotAllowedException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "contas_bancarias",
        uniqueConstraints = @UniqueConstraint(columnNames = {"agencia", "conta", "fk_banco"}))
public class ContaBancaria implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_conta_bancaria")
    private Long idContaBancaria;

    @Column(name = "agencia")
    private String agencia;

    @Column(name = "conta")
    private String conta;

    @Column(name = "saldo", precision = 14, scale = 5)
    private BigDecimal saldo;

    @ManyToOne
    @JoinColumn(name = "fk_banco")
    private Banco banco;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contaBancaria")
    private List<Transacao> transacoes = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "contaBancaria")
    private Set<CartaoCredito> creditCards = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "fk_usuario")
    private Usuario usuario;

    public ContaBancaria(Long idContaBancaria, String agencia, String conta, BigDecimal saldo) {
        this.idContaBancaria = idContaBancaria;
        this.agencia = agencia;
        this.conta = conta;
        this.saldo = saldo;
    }

    public void depositar(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new TransactionNotAllowedException("Valor inválido para depósito");
        }

        saldo = saldo.add(valor);
    }

    public void sacar(BigDecimal valor) {
        saldo = saldo.subtract(valor);
        if (saldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new TransactionNotAllowedException("Saldo insuficiente para operação");
        }
    }

    public void adicionarTransacaoEAtualizarSaldo(Transacao transacao) {
        switch (transacao.getTipoTransacao()) {
            case ENTRADA:
                depositar(transacao.getValor());
                break;

            case SAIDA:
                sacar(transacao.getValor());
                break;

            default:
                throw new TransactionNotAllowedException("Transação não permitida");
        }

        transacoes.add(transacao);
    }
}
