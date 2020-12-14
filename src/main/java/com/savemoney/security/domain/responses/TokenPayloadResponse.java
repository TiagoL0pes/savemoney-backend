package com.savemoney.security.domain.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TokenPayloadResponse {
    private Long idContaBancaria;
    private String email;
    private String agencia;
    private String conta;
}
