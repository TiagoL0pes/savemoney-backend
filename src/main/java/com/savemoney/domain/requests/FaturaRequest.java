package com.savemoney.domain.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FaturaRequest {

    @Positive
    private Long idCartao;

    private Integer mes;

    private Integer ano;
}
