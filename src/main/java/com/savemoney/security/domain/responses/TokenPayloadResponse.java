package com.savemoney.security.domain.responses;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenPayloadResponse {
    private Long idUsuario;
    private Long idContaBancaria;
    private String email;
    private String agencia;
    private String conta;
}
