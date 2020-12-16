package com.savemoney.security.domain.requests;

import com.savemoney.domain.requests.ContaBancariaRequest;
import com.savemoney.validations.annotations.EmailNovoUsuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequest {

    @EmailNovoUsuario
    private String email;

    @NotEmpty
    private String senha;

    private ContaBancariaRequest contaBancaria;
}
