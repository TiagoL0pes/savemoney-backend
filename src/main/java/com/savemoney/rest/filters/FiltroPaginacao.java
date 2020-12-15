package com.savemoney.rest.filters;

import com.savemoney.domain.models.ContaBancaria;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FiltroPaginacao {

    private Integer mes;
    private Integer ano;
    private ContaBancaria contaBancaria;

    public FiltroPaginacao(Integer mes, Integer ano) {
        this.mes = mes;
        this.ano = ano;
    }
}
