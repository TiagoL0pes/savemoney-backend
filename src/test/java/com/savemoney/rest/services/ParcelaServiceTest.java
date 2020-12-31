package com.savemoney.rest.services;

import br.com.six2six.fixturefactory.Fixture;
import com.savemoney.domain.models.ContaBancaria;
import com.savemoney.domain.models.Parcela;
import com.savemoney.rest.repositories.ParcelaRepository;
import com.savemoney.templates.models.ContaBancariaTemplate;
import com.savemoney.templates.models.ParcelaTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ParcelaServiceTest {

    @Mock
    private ParcelaRepository parcelaRepository;

    @InjectMocks
    private ParcelaService parcelaService;

    @BeforeEach
    public void setup() {
        loadTemplates("com.savemoney.templates");
    }

    @Test
    public void deveBuscarParcelaPorId() {
        Parcela parcelaEsperada = Fixture.from(Parcela.class)
                .gimme(ParcelaTemplate.PRIMEIRA);
        final Long idParcela = 1L;

        Mockito.when(parcelaRepository.findById(anyLong()))
                .thenReturn(Optional.of(parcelaEsperada));

        Parcela parcela = parcelaService.bucarPorId(idParcela);

        assertNotNull(parcela);
    }

    @Test
    public void deveAtualizarStatusParcela() {
        Parcela parcela = Fixture.from(Parcela.class)
                .gimme(ParcelaTemplate.PRIMEIRA);

        Mockito.when(parcelaRepository.save(any(Parcela.class)))
                .thenReturn(parcela);

        parcelaService.atualizarStatus(parcela);
    }

    @Test
    public void deveBuscarParcelasParaGerarFatura() {
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);
        List<Parcela> parcelasEsperadas = Fixture.from(Parcela.class)
                .gimme(3, ParcelaTemplate.PRIMEIRA,
                        ParcelaTemplate.SEGUNDA,
                        ParcelaTemplate.TERCEIRA);
        final LocalDate dataVencimento = LocalDate.of(2020, 12, 10);

        Mockito.when(parcelaRepository.buscarParcelasParaGerarFatura(anyInt(), anyInt(), any(ContaBancaria.class)))
                .thenReturn(parcelasEsperadas);

        List<Parcela> parcelas = parcelaService.buscarParcelasParaGerarFatura(dataVencimento, contaBancaria);

        assertNotNull(parcelas);
    }
}
