package com.savemoney.domain.models;

import com.savemoney.utils.exceptions.TransactionNotAllowedException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
@Table(name = "cartoes_credito")
public class CartaoCredito implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cartao_credito")
    private Long idCartaoCredito;

    @EqualsAndHashCode.Include
    @Column(name = "numero", unique = true)
    private String numero;

    @Column(name = "dia_vencimento")
    private Integer diaVencimento;

    @Column(name = "limite_credito", precision = 14, scale = 5)
    private BigDecimal limiteCredito;

    @Column(name = "limite_utilizado", precision = 14, scale = 5)
    private BigDecimal limiteUtilizado;

    @ManyToOne
    @JoinColumn(name = "fk_conta_bancaria")
    private ContaBancaria contaBancaria;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_item_cartao")
    private List<ItemCartao> itens = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_fatura")
    private List<Fatura> faturas = new ArrayList<>();

    public CartaoCredito(Long idCartaoCredito,
                         String numero,
                         Integer diaVencimento,
                         BigDecimal limiteCredito) {
        this.idCartaoCredito = idCartaoCredito;
        this.numero = numero;
        this.diaVencimento = diaVencimento;
        this.limiteCredito = limiteCredito;
    }

    public void adicionarItem(ItemCartao item) {
        itens.add(item);
        atualizarLimiteUtilizado();
    }

    public void adicionarFatura(Fatura fatura) {
        faturas.add(fatura);
    }

    private void atualizarLimiteUtilizado() {
        limiteUtilizado = itens.stream().map(ItemCartao::getValor)
                .reduce(BigDecimal::add)
                .get();

        if (limiteCredito.compareTo(limiteUtilizado) <= 0) {
            throw new TransactionNotAllowedException("Cliente não possui limite suficiente");
        }
    }

    public void atualizarItemCartao(ItemCartao itemAntigo, ItemCartao novoItem) {
        itens.remove(itemAntigo);
        atualizarLimiteDisponivel(itemAntigo);
        itens.add(novoItem);
        atualizarLimiteUtilizado();
    }

    private void atualizarLimiteDisponivel(ItemCartao itemCartao) {
        itens = itens.stream()
                .filter(item -> !item.getIdItemCartao().equals(itemCartao.getIdItemCartao()))
                .collect(Collectors.toList());

        limiteUtilizado = limiteUtilizado.subtract(itemCartao.getValor());
    }
}
