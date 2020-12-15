package com.savemoney.security.domain.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
public class AuthRequest {

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String senha;

    public UsernamePasswordAuthenticationToken generateCredencials(Collection<? extends GrantedAuthority> authorities) {
        return new UsernamePasswordAuthenticationToken(email, senha, authorities);
    }
}
