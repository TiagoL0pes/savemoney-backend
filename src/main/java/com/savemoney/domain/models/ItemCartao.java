package com.savemoney.domain.models;

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
@Table(name = "item_cartao")
public class ItemCartao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item_cartao")
    private Long idItemCartao;

    @Column(name = "data_compra")
    private LocalDate dataCompra;

    @Column(name = "descricao", length = 20)
    private String descricao;

    @Column(name = "valor_total", precision = 14, scale = 5)
    private BigDecimal valorTotal;

    @Column(name = "numero_parcelas")
    private Integer numeroParcelas;

    @ManyToOne
    @JoinColumn(name = "fk_conta_bancaria")
    private ContaBancaria contaBancaria;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "fk_parcela")
    private List<Parcela> parcelas = new ArrayList<>();

    public ItemCartao(Long idItemCartao,
                      LocalDate dataCompra,
                      String descricao,
                      BigDecimal valor,
                      Integer numeroParcelas) {
        this.idItemCartao = idItemCartao;
        this.dataCompra = dataCompra;
        this.descricao = descricao;
        this.valorTotal = valor;
        this.numeroParcelas = numeroParcelas;
    }

    public void adicionarParcela(Parcela parcela) {
        parcelas.add(parcela);
    }

    public BigDecimal getValorParcela() {
        return valorTotal.divide(new BigDecimal(numeroParcelas), RoundingMode.HALF_EVEN);
    }

    public void atualizarItemCartao(Parcela parcelaAntiga, Parcela novaParcela) {
        parcelas.remove(parcelaAntiga);
        parcelas.add(novaParcela);
    }
}
