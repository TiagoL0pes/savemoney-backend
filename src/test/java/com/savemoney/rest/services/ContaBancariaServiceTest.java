package com.savemoney.rest.services;

import br.com.six2six.fixturefactory.Fixture;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.savemoney.domain.models.ContaBancaria;
import com.savemoney.domain.pagination.ContasBancariasPagination;
import com.savemoney.domain.requests.ContaBancariaRequest;
import com.savemoney.rest.repositories.BancoRepository;
import com.savemoney.rest.repositories.ContaBancariaRepository;
import com.savemoney.security.domain.responses.TokenPayloadResponse;
import com.savemoney.security.utils.JSONUtil;
import com.savemoney.security.utils.JwtUtil;
import com.savemoney.templates.models.ContaBancariaTemplate;
import com.savemoney.templates.requests.ContaBancariaRequestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class ContaBancariaServiceTest {

    @Mock
    private ContaBancariaRepository contaBancariaRepository;

    @Mock
    private BancoRepository bancoRepository;

    private JwtUtil jwtUtil;
    private JSONUtil jsonUtil;

    @InjectMocks
    private ContaBancariaService contaBancariaService;

    @BeforeEach
    public void setup() {
        loadTemplates("com.savemoney.templates");
        jwtUtil = new JwtUtil();
        jsonUtil = new JSONUtil();
        ReflectionTestUtils.setField(jwtUtil, "secret", "8f447cd98e8dd5b908b53d4e4936d4c9", String.class);
        ReflectionTestUtils.setField(jwtUtil, "expiration", 86400000L, Long.class);
    }

    @Test
    public void deveAdicionarContaBancaria() {
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);

        Mockito.when(contaBancariaRepository.save(any(ContaBancaria.class)))
                .thenReturn(contaBancaria);

        ContaBancaria contaBancariaAdicionada = contaBancariaService.adicionar(contaBancaria);

        assertNotNull(contaBancariaAdicionada);
    }

    @Test
    public void deveBuscarContaBancariaPorId() {
        ContaBancaria contaBancariaEsperada = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        final Long idContaBancaria = 1L;

        Mockito.when(contaBancariaRepository.findById(anyLong()))
                .thenReturn(Optional.of(contaBancariaEsperada));

        ContaBancaria contaBancaria = contaBancariaService.buscarPorId(idContaBancaria);

        assertNotNull(contaBancaria);
        assertEquals(contaBancariaEsperada, contaBancaria);
    }

    @Test
    public void deveListarContasBancarias() {
        List<ContaBancaria> contasBancarias = Fixture.from(ContaBancaria.class)
                .gimme(1, ContaBancariaTemplate.VALIDO);
        final int pagina = 0;
        final int tamanho = 10;

        Page<ContaBancaria> pageContasBancarias = new PageImpl<>(contasBancarias);
        Mockito.when(contaBancariaRepository.findAll(any(Pageable.class)))
                .thenReturn(pageContasBancarias);

        Page<ContaBancaria> pageListaContasBancarias =
                contaBancariaService.listar(PageRequest.of(pagina, tamanho));

        assertNotNull(pageListaContasBancarias);
    }

    @Test
    public void devAtualizarContaBancaria() {
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        ContaBancariaRequest request = Fixture.from(ContaBancariaRequest.class)
                .gimme(ContaBancariaRequestTemplate.VALIDO);
        final Long idContaBancaria = 1L;

        Mockito.when(contaBancariaRepository.findById(anyLong()))
                .thenReturn(Optional.of(contaBancaria));
        Mockito.when(contaBancariaRepository.save(any(ContaBancaria.class)))
                .thenReturn(contaBancaria);

        ContaBancaria contaBancariaAtualizada = contaBancariaService.atualizar(idContaBancaria, request);

        assertNotNull(contaBancariaAtualizada);
    }

    @Test
    public void deveAtualizarSaldoContaBancaria() {
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);

        Mockito.when(contaBancariaRepository.save(any(ContaBancaria.class)))
                .thenReturn(contaBancaria);

        contaBancariaService.atualizarSaldo(contaBancaria);
    }

    @Test
    public void deveRemoverContaBancaria() {
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        final Long idContaBancaria = 1L;

        Mockito.when(contaBancariaRepository.findById(anyLong()))
                .thenReturn(Optional.of(contaBancaria));

        contaBancariaService.remover(idContaBancaria);
    }

    @Test
    public void deveRecuperarContaBancaria() {
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        final String token = gerarTokenValido(contaBancaria);

        TokenPayloadResponse payload = jwtUtil.getPayloadFromToken(token);

        assertNotNull(payload);
    }

    private String gerarTokenValido(ContaBancaria contaBancaria) {
        TokenPayloadResponse tokenPayload = TokenPayloadResponse.builder()
                .idContaBancaria(contaBancaria.getIdContaBancaria())
                .email("admin@email.com")
                .agencia(contaBancaria.getAgencia())
                .conta(contaBancaria.getConta())
                .build();

        String payload = jsonUtil.toJson(tokenPayload);
        return "Bearer " + jwtUtil.generateToken(payload);
    }
}
