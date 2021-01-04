package com.savemoney.rest.commands;

import br.com.six2six.fixturefactory.Fixture;
import com.savemoney.domain.models.ContaBancaria;
import com.savemoney.domain.models.Despesa;
import com.savemoney.domain.models.Transacao;
import com.savemoney.domain.requests.TransacaoRequest;
import com.savemoney.rest.services.TransacaoService;
import com.savemoney.templates.models.ContaBancariaTemplate;
import com.savemoney.templates.models.DespesaTemplate;
import com.savemoney.templates.models.TransacaoTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class PendenteCommandTest {

    @Mock
    private TransacaoService transacaoService;

    @InjectMocks
    private PendenteCommand command;

    @BeforeEach
    public void setup() {
        loadTemplates("com.savemoney.templates");
    }

    @Test
    public void deveRealizarPagamentoDespesa() {
        Transacao transacao = Fixture.from(Transacao.class)
                .gimme(TransacaoTemplate.SAIDA);
        Despesa despesa = Fixture.from(Despesa.class)
                .gimme(DespesaTemplate.PENDENTE);
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);

        Mockito.when(transacaoService.gerarNovaTransacao(any(TransacaoRequest.class)))
                .thenReturn(transacao);
        Mockito.when(transacaoService.adicionar(any(Transacao.class)))
                .thenReturn(transacao);

        command.execute(despesa, contaBancaria);
    }
}
