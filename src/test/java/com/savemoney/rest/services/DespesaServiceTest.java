package com.savemoney.rest.services;

import br.com.six2six.fixturefactory.Fixture;
import com.savemoney.domain.models.ContaBancaria;
import com.savemoney.domain.models.Despesa;
import com.savemoney.domain.requests.DespesaRequest;
import com.savemoney.rest.filters.FiltroPaginacao;
import com.savemoney.rest.repositories.DespesaRepository;
import com.savemoney.templates.models.ContaBancariaTemplate;
import com.savemoney.templates.models.DespesaTemplate;
import com.savemoney.templates.requests.DespesaRequestTemplate;
import com.savemoney.utils.exceptions.TransactionNotAllowedException;
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
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class DespesaServiceTest {

    @Mock
    private DespesaRepository despesaRepository;

    @InjectMocks
    private DespesaService despesaService;

    @BeforeEach
    public void setup() {
        loadTemplates("com.savemoney.templates");
    }

    @Test
    public void deveAdicionarDespesa() {
        Despesa despesa = Fixture.from(Despesa.class)
                .gimme(DespesaTemplate.PENDENTE);

        Mockito.when(despesaRepository.save(any(Despesa.class)))
                .thenReturn(despesa);

        Despesa despesaAtualizada = despesaService.adicionar(despesa);

        assertNotNull(despesaAtualizada);
    }

    @Test
    public void deveBuscarDespesaPorId() {
        Despesa despesaEsperada = Fixture.from(Despesa.class)
                .gimme(DespesaTemplate.PENDENTE);
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        final Long idDespesa = 1L;

        Mockito.when(despesaRepository.findByIdDespesaAndContaBancaria(anyLong(), any(ContaBancaria.class)))
                .thenReturn(Optional.of(despesaEsperada));

        Despesa despesa = despesaService.buscarPorId(idDespesa, contaBancaria);

        assertNotNull(despesa);
        assertEquals(despesaEsperada, despesa);
    }

    @Test
    public void deveListarDespesas() {
        List<Despesa> despesas = Fixture.from(Despesa.class)
                .gimme(1, DespesaTemplate.PENDENTE);
        final int pagina = 0;
        final int tamanho = 10;

        FiltroPaginacao filtro = new FiltroPaginacao();
        Page<Despesa> pageDespesas = new PageImpl<>(despesas);
        Mockito.when(despesaRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(pageDespesas);

        Page<Despesa> pageListaDespesas =
                despesaService.listar(filtro, PageRequest.of(pagina, tamanho));

        assertNotNull(pageListaDespesas);
    }

    @Test
    public void devAtualizarStatusDespesa() {
        Despesa despesa = Fixture.from(Despesa.class)
                .gimme(DespesaTemplate.PENDENTE);

        Mockito.when(despesaRepository.save(any(Despesa.class)))
                .thenReturn(despesa);

        despesaService.atualizarStatus(despesa);
    }

    @Test
    public void deveAtualizarDespesa() {
        Despesa despesa = Fixture.from(Despesa.class)
                .gimme(DespesaTemplate.PENDENTE);
        DespesaRequest request = Fixture.from(DespesaRequest.class)
                .gimme(DespesaRequestTemplate.VALIDO);

        Mockito.when(despesaRepository.save(any(Despesa.class)))
                .thenReturn(despesa);

        Despesa despesaAtualizada = despesaService.atualizar(despesa, request);

        assertNotNull(despesaAtualizada);
    }

    @Test
    public void deveRemoverDespesa() {
        Despesa despesa = Fixture.from(Despesa.class)
                .gimme(DespesaTemplate.PENDENTE);

        despesaService.remover(despesa);
    }

    @Test
    public void deveLancarExcecaoAoTentarRemoverDespesaPaga() {
        Despesa despesa = Fixture.from(Despesa.class)
                .gimme(DespesaTemplate.PAGO);

        assertThrows(TransactionNotAllowedException.class, () -> {
            despesaService.remover(despesa);
        });
    }

}
