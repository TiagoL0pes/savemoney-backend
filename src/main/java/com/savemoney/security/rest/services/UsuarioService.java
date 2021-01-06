package com.savemoney.security.rest.services;

import com.savemoney.domain.models.Banco;
import com.savemoney.domain.models.ContaBancaria;
import com.savemoney.domain.responses.UsuarioResponse;
import com.savemoney.rest.repositories.BancoRepository;
import com.savemoney.rest.repositories.ContaBancariaRepository;
import com.savemoney.security.domain.mappers.UsuarioMapper;
import com.savemoney.security.domain.models.Usuario;
import com.savemoney.security.domain.requests.UsuarioRequest;
import com.savemoney.security.domain.responses.TokenPayloadResponse;
import com.savemoney.security.rest.repositories.UsuarioRepository;
import com.savemoney.security.utils.JwtUtil;
import com.savemoney.utils.exceptions.ResourceNotFoundException;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BancoRepository bancoRepository;

    @Autowired
    private ContaBancariaRepository contaBancariaRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private JwtUtil jwtUtil;

    private final UsuarioMapper usuarioMapper = Mappers.getMapper(UsuarioMapper.class);

    @Transactional(rollbackFor = DataIntegrityViolationException.class)
    public Usuario adicionar(UsuarioRequest request) {
        Usuario usuario = usuarioMapper.toUsuario(request, encoder);
        usuario = usuarioRepository.save(usuario);

        if (Objects.nonNull(request.getContaBancaria()) &&
                Objects.nonNull(request.getContaBancaria().getIdBanco())) {
            Banco banco = bancoRepository.findById(request.getContaBancaria().getIdBanco())
                    .orElseThrow(() -> new ResourceNotFoundException("Banco não encontrado"));

            ContaBancaria contaBancaria = usuarioMapper.toContaBancaria(request);
            contaBancaria.setBanco(banco);
            contaBancaria.setUsuario(usuario);
            contaBancariaRepository.save(contaBancaria);
        }

        return usuario;
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

    }

    public Usuario recuperarUsuario(String token) {
        TokenPayloadResponse payload = jwtUtil.getPayloadFromToken(token);
        return buscarPorId(payload.getIdUsuario());
    }
}
