package com.savemoney.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Mes {

    JANEIRO(1),
    FEVEREIRO(2),
    MARCO(3),
    ABRIL(4),
    MAIO(5),
    JUNHO(6),
    JULHO(7),
    AGOSTO(8),
    SETEMBRO(9),
    OUTUBRO(10),
    NOVEMBRO(11),
    DEZEMBRO(12);

    @Getter
    private final Integer numeroMes;

    public static Mes getNumeroMes(Integer request) {
        for (Mes mes : Mes.values()) {
            if (mes.getNumeroMes().compareTo(request) == 0) {
                return mes;
            }
        }
        return null;
    }
}
