package com.savemoney.security.domain.requests;

import com.savemoney.domain.requests.ContaBancariaRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequest {

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String senha;

    private ContaBancariaRequest contaBancaria;
}
