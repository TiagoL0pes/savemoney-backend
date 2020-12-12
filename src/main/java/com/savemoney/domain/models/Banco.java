package com.savemoney.domain.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "bancos")
public class Banco implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_banco")
    private Long idBanco;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "nome")
    private String nome;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "banco", cascade = CascadeType.ALL)
    private List<ContaBancaria> contasBancarias = new ArrayList<>();

    public Banco(Long idBanco, String codigo, String nome) {
        this.idBanco = idBanco;
        this.codigo = codigo;
        this.nome = nome;
    }
}
