package com.savemoney.rest.facades;

import br.com.six2six.fixturefactory.Fixture;
import com.savemoney.domain.models.ContaBancaria;
import com.savemoney.domain.models.Despesa;
import com.savemoney.domain.pagination.DespesasPagination;
import com.savemoney.domain.requests.DespesaRequest;
import com.savemoney.domain.responses.DespesaResponse;
import com.savemoney.rest.filters.FiltroPaginacao;
import com.savemoney.rest.services.ContaBancariaService;
import com.savemoney.rest.services.DespesaService;
import com.savemoney.templates.models.ContaBancariaTemplate;
import com.savemoney.templates.models.DespesaTemplate;
import com.savemoney.templates.requests.DespesaRequestTemplate;
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

import java.util.List;

import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class DespesaFacadeTest {

    @Mock
    private DespesaService despesaService;

    @Mock
    private ContaBancariaService contaBancariaService;

    @InjectMocks
    private DespesaFacade despesaFacade;

    private String token;

    @BeforeEach
    public void setup() {
        loadTemplates("com.savemoney.templates");
        token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ7XCJpZENvbnRhQmFuY2FyaWFcIjoxLFwiZW1haWxcIjpcImFkbWluQGVtYWlsLmNvbVwiLFwiYWdlbmNpYVwiOlwiMDAwMVwiLFwiY29udGFcIjpcIjEyMzQ1NlwifSIsImV4cCI6MTYwOTAwMjU4Mn0.6-Cf6-U41ETexS7qAQt7K_iMCy8xDT_3PAfdxTQG8E9bYV0Law6V_8ld_qwzlrsB8K2ShBTKy7A02TWXEGfStQ";
    }

    @Test
    public void deveAdicionarDespesa() {
        Despesa despesa = Fixture.from(Despesa.class)
                .gimme(DespesaTemplate.PENDENTE);
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        DespesaRequest request = Fixture.from(DespesaRequest.class)
                .gimme(DespesaRequestTemplate.VALIDO);

        Mockito.when(contaBancariaService.recuperarContaBancaria(anyString()))
                .thenReturn(contaBancaria);
        Mockito.when(despesaService.adicionar(any(Despesa.class)))
                .thenReturn(despesa);

        Despesa despesaAdicionada = despesaFacade.adicionar(token, request);

        assertNotNull(despesaAdicionada);
    }

    @Test
    public void deveBuscarDespesaPorId() {
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        Despesa despesa = Fixture.from(Despesa.class)
                .gimme(DespesaTemplate.PENDENTE);
        final Long idDespesa = 1L;

        Mockito.when(contaBancariaService.recuperarContaBancaria(anyString()))
                .thenReturn(contaBancaria);
        Mockito.when(despesaService.buscarPorId(anyLong(), any(ContaBancaria.class)))
                .thenReturn(despesa);

        DespesaResponse response = despesaFacade.buscarPorId(token, idDespesa);

        assertNotNull(response);
    }

    @Test
    public void deveListarDespesas() {
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        List<Despesa> despesas = Fixture.from(Despesa.class)
                .gimme(1, DespesaTemplate.PENDENTE);
        final int pagina = 0;
        final int tamanho = 10;

        FiltroPaginacao filtro = new FiltroPaginacao();
        Page<Despesa> pageDespesa = new PageImpl<Despesa>(despesas);
        Mockito.when(contaBancariaService.recuperarContaBancaria(anyString()))
                .thenReturn(contaBancaria);
        Mockito.when(despesaService.listar(any(FiltroPaginacao.class), any(Pageable.class)))
                .thenReturn(pageDespesa);

        DespesasPagination despesasPaginadas =
                despesaFacade.listar(token, filtro, PageRequest.of(pagina, tamanho));

        assertNotNull(despesasPaginadas);
    }

    @Test
    public void deveAtualizarDespesa() {
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        Despesa despesa = Fixture.from(Despesa.class)
                .gimme(DespesaTemplate.PENDENTE);
        DespesaRequest request = Fixture.from(DespesaRequest.class)
                .gimme(DespesaRequestTemplate.VALIDO);
        final Long idDespesa = 1L;

        Mockito.when(contaBancariaService.recuperarContaBancaria(anyString()))
                .thenReturn(contaBancaria);
        Mockito.when(despesaService.buscarPorId(anyLong(), any(ContaBancaria.class)))
                .thenReturn(despesa);
        Mockito.when(despesaService.atualizar(any(Despesa.class), any(DespesaRequest.class)))
                .thenReturn(despesa);

        DespesaResponse response = despesaFacade.atualizar(token, idDespesa, request);

        assertNotNull(response);
    }

    @Test
    public void deveRemoverDespesa() {
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        Despesa despesa = Fixture.from(Despesa.class)
                .gimme(DespesaTemplate.PENDENTE);
        final Long idDespesa = 1L;

        Mockito.when(contaBancariaService.recuperarContaBancaria(anyString()))
                .thenReturn(contaBancaria);
        Mockito.when(despesaService.buscarPorId(anyLong(), any(ContaBancaria.class)))
                .thenReturn(despesa);

        despesaFacade.remover(token, idDespesa);
    }

}
