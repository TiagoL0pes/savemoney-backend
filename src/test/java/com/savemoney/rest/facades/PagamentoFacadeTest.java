package com.savemoney.rest.facades;

import br.com.six2six.fixturefactory.Fixture;
import com.savemoney.domain.models.ContaBancaria;
import com.savemoney.domain.models.Despesa;
import com.savemoney.domain.models.Fatura;
import com.savemoney.rest.commands.PendenteCommand;
import com.savemoney.rest.factories.PagamentoCommandFactory;
import com.savemoney.rest.services.ContaBancariaService;
import com.savemoney.rest.services.DespesaService;
import com.savemoney.rest.services.FaturaService;
import com.savemoney.rest.services.ParcelaService;
import com.savemoney.templates.models.ContaBancariaTemplate;
import com.savemoney.templates.models.DespesaTemplate;
import com.savemoney.templates.models.FaturaTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class PagamentoFacadeTest {

    @Mock
    private DespesaService despesaService;

    @Mock
    private ContaBancariaService contaBancariaService;

    @Mock
    private ParcelaService parcelaService;

    @Mock
    private FaturaService faturaService;

    @Mock
    private PagamentoCommandFactory pagamentoCommandFactory;

    @InjectMocks
    private PagamentoFacade pagamentoFacade;

    private String token;

    @BeforeEach
    public void setup() {
        loadTemplates("com.savemoney.templates");
        token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ7XCJpZENvbnRhQmFuY2FyaWFcIjoxLFwiZW1haWxcIjpcImFkbWluQGVtYWlsLmNvbVwiLFwiYWdlbmNpYVwiOlwiMDAwMVwiLFwiY29udGFcIjpcIjEyMzQ1NlwifSIsImV4cCI6MTYwOTAwMjU4Mn0.6-Cf6-U41ETexS7qAQt7K_iMCy8xDT_3PAfdxTQG8E9bYV0Law6V_8ld_qwzlrsB8K2ShBTKy7A02TWXEGfStQ";
    }

    @Test
    public void devePagarDespesa() {
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        Despesa despesa = Fixture.from(Despesa.class)
                .gimme(DespesaTemplate.PENDENTE);
        PendenteCommand command = Mockito.mock(PendenteCommand.class);
        final Long idDespesa = 1L;

        Mockito.when(contaBancariaService.recuperarContaBancaria(anyString()))
                .thenReturn(contaBancaria);
        Mockito.when(despesaService.buscarPorId(anyLong(), any(ContaBancaria.class)))
                .thenReturn(despesa);
        Mockito.when(pagamentoCommandFactory.getCommand(any()))
                .thenReturn(command);

        pagamentoFacade.pagarDespesa(token, idDespesa);
    }

    @Test
    public void devePagarFatura() {
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        Fatura fatura = Fixture.from(Fatura.class)
                .gimme(FaturaTemplate.VALIDO);
        PendenteCommand command = Mockito.mock(PendenteCommand.class);
        final Long idDespesa = 1L;

        Mockito.when(contaBancariaService.recuperarContaBancaria(anyString())).
                thenReturn(contaBancaria);
        Mockito.when(faturaService.buscarPorId(anyLong()))
                .thenReturn(fatura);
        Mockito.when(pagamentoCommandFactory.getCommand(any()))
                .thenReturn(command);

        pagamentoFacade.pagarFatura(token, idDespesa);
    }
}
