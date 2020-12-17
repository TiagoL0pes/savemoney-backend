package com.savemoney.domain.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BancoResponse {

    private Long idBanco;

    private String codigo;

    private String nome;
}
