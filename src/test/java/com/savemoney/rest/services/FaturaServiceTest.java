package com.savemoney.rest.services;

import br.com.six2six.fixturefactory.Fixture;
import com.savemoney.domain.models.Fatura;
import com.savemoney.domain.pagination.FaturasPagination;
import com.savemoney.rest.repositories.FaturaRepository;
import com.savemoney.templates.models.FaturaTemplate;
import com.savemoney.utils.exceptions.BadRequestException;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class FaturaServiceTest {

    @Mock
    private FaturaRepository faturaRepository;

    @InjectMocks
    private FaturaService faturaService;

    @BeforeEach
    public void setup() {
        loadTemplates("com.savemoney.templates");
    }

    @Test
    public void deveGerarFatura() {
        Fatura fatura = Fixture.from(Fatura.class)
                .gimme(FaturaTemplate.PENDENTE);

        Mockito.when(faturaRepository.save(any(Fatura.class)))
                .thenReturn(fatura);

        Fatura faturaGerada = faturaService.gerar(fatura);

        assertNotNull(faturaGerada);
    }

    @Test
    public void deveBuscarFaturaPorId() {
        Fatura faturaEsperada = Fixture.from(Fatura.class)
                .gimme(FaturaTemplate.PENDENTE);
        final Long idFatura = 1L;

        Mockito.when(faturaRepository.findById(anyLong()))
                .thenReturn(Optional.of(faturaEsperada));

        Fatura fatura = faturaService.buscarPorId(idFatura);

        assertNotNull(fatura);
        assertEquals(faturaEsperada, fatura);
    }

    @Test
    public void deveListarFaturas() {
        List<Fatura> faturas = Fixture.from(Fatura.class)
                .gimme(1, FaturaTemplate.PENDENTE);
        final Long idFatura = 1L;
        final int pagina = 0;
        final int tamanho = 10;

        Page<Fatura> pageFaturas = new PageImpl<>(faturas);
        Mockito.when(faturaRepository.buscarTodosPorCartaoCredito(anyLong(), any(Pageable.class)))
                .thenReturn(pageFaturas);

        FaturasPagination faturasPagination =
                faturaService.listar(idFatura, PageRequest.of(pagina, tamanho));

        assertNotNull(faturasPagination);
    }

    @Test
    public void deveLancarExcecaoQuandoFaturaParaMesJaFoiGerada() {
        Fatura fatura = Fixture.from(Fatura.class)
                .gimme(FaturaTemplate.PENDENTE);
        final LocalDate dataVencimento = LocalDate.now();

        Mockito.when(faturaRepository.buscarPorDataVencimento(anyInt(), anyInt()))
                .thenReturn(Optional.of(fatura));

        assertThrows(BadRequestException.class, () -> {
            faturaService.buscarPorDataVencimento(dataVencimento);
        });
    }

    @Test
    public void deveBuscarFaturaPorDataVencimento() {
        final LocalDate dataVencimento = LocalDate.now();

        Mockito.when(faturaRepository.buscarPorDataVencimento(anyInt(), anyInt()))
                .thenReturn(Optional.empty());

        faturaService.buscarPorDataVencimento(dataVencimento);
    }

    @Test
    public void deveAtualizarStatusFatura() {
        Fatura fatura = Fixture.from(Fatura.class)
                .gimme(FaturaTemplate.PENDENTE);

        Mockito.when(faturaRepository.save(any(Fatura.class)))
                .thenReturn(fatura);

        faturaService.atualizarStatus(fatura);
    }

}
