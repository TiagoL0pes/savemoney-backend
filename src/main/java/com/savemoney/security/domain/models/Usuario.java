package com.savemoney.security.domain.models;

import com.savemoney.domain.models.ContaBancaria;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "conta_nao_expirada", columnDefinition = "boolean DEFAULT true", nullable = false)
    private Boolean contaNaoExpirada = true;

    @Column(name = "conta_nao_bloqueada", columnDefinition = "boolean DEFAULT true", nullable = false)
    private Boolean contaNaoBloqueada = true;

    @Column(name = "credenciais_nao_expiradas", columnDefinition = "boolean DEFAULT true", nullable = false)
    private Boolean CredenciaisNaoExpiradas = true;

    @Column(name = "habilitado", columnDefinition = "boolean DEFAULT true", nullable = false)
    private Boolean habilitado = true;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ContaBancaria contaBancaria;

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.contaNaoExpirada;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.contaNaoBloqueada;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.CredenciaisNaoExpiradas;
    }

    @Override
    public boolean isEnabled() {
        return this.habilitado;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    public Usuario(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

}
