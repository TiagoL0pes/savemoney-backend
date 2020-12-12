package com.savemoney.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum StatusPagamento {

    PENDENT("Pendent"),
    PAID("Paid");

    @Getter
    private String status;

    public static StatusPagamento getStatus(String request) {
        for (StatusPagamento statusPagamento : StatusPagamento.values()) {
            if (statusPagamento.status.equals(request)) {
                return statusPagamento;
            }
        }
        return null;
    }
}
