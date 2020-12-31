package com.savemoney.rest.services;

import br.com.six2six.fixturefactory.Fixture;
import com.savemoney.domain.models.Transacao;
import com.savemoney.domain.requests.TransacaoRequest;
import com.savemoney.rest.repositories.TransacaoRepository;
import com.savemoney.templates.models.TransacaoTemplate;
import com.savemoney.templates.requests.TransacaoRequestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {

    @Mock
    private TransacaoRepository transacaoRepository;

    @InjectMocks
    private TransacaoService transacaoService;

    @BeforeEach
    public void setup() {
        loadTemplates("com.savemoney.templates");
    }

    @Test
    public void deveAdicionarTransacao() {
        Transacao transacao = Fixture.from(Transacao.class)
                .gimme(TransacaoTemplate.ENTRADA);

        Mockito.when(transacaoRepository.save(any(Transacao.class)))
                .thenReturn(transacao);

        Transacao transacaoAdicionada = transacaoService.adicionar(transacao);

        assertNotNull(transacaoAdicionada);
    }

    @Test
    public void deveGerarNovaTransacao() {
        TransacaoRequest request = Fixture.from(TransacaoRequest.class)
                .gimme(TransacaoRequestTemplate.ENTRADA);

        Transacao transacao = transacaoService.gerarNovaTransacao(request);

        assertNotNull(transacao);
    }
}
