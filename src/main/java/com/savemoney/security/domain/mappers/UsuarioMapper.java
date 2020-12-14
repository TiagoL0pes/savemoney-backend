package com.savemoney.security.domain.mappers;

import com.savemoney.domain.models.ContaBancaria;
import com.savemoney.security.domain.models.Usuario;
import com.savemoney.security.domain.requests.UsuarioRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Mapper
public interface UsuarioMapper {

    @Mapping(target = "senha", expression = "java(criptografarSenha(request.getSenha(), encoder))")
    @Mapping(target = "contaBancaria", ignore = true)
    Usuario toUsuario(UsuarioRequest request, BCryptPasswordEncoder encoder);

    @Mapping(target = "agencia", source = "request.contaBancaria.agencia")
    @Mapping(target = "conta", source = "request.contaBancaria.conta")
    @Mapping(target = "saldo", source = "request.contaBancaria.saldo")
    ContaBancaria toContaBancaria(UsuarioRequest request);

    @Named("criptografarSenha")
    default String criptografarSenha(String senha, BCryptPasswordEncoder encoder) {
        return encoder.encode(senha);
    }
}
