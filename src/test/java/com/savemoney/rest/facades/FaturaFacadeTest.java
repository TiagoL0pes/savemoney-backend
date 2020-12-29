package com.savemoney.rest.facades;

import br.com.six2six.fixturefactory.Fixture;
import com.savemoney.domain.models.CartaoCredito;
import com.savemoney.domain.models.ContaBancaria;
import com.savemoney.domain.models.Fatura;
import com.savemoney.domain.models.Parcela;
import com.savemoney.domain.pagination.FaturasPagination;
import com.savemoney.domain.requests.FaturaRequest;
import com.savemoney.domain.responses.FaturaResponse;
import com.savemoney.rest.services.CartaoCreditoService;
import com.savemoney.rest.services.FaturaService;
import com.savemoney.rest.services.ParcelaService;
import com.savemoney.templates.models.CartaoCreditoTemplate;
import com.savemoney.templates.models.FaturaTemplate;
import com.savemoney.templates.models.ParcelaTemplate;
import com.savemoney.templates.requests.FaturaRequestTemplate;
import com.savemoney.templates.responses.FaturaResponseTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class FaturaFacadeTest {

    @Mock
    private FaturaService faturaService;

    @Mock
    private CartaoCreditoService cartaoCreditoService;

    @Mock
    private ParcelaService parcelaService;

    @InjectMocks
    private FaturaFacade faturaFacade;

    @BeforeEach
    public void setup() {
        loadTemplates("com.savemoney.templates");
    }

    @Test
    public void deveGerarFatura() {
        CartaoCredito cartaoCredito = Fixture.from(CartaoCredito.class)
                .gimme(CartaoCreditoTemplate.COM_CONTA_BANCARIA);
        List<Parcela> parcelas = Fixture.from(Parcela.class)
                .gimme(3,
                        ParcelaTemplate.PRIMEIRA,
                        ParcelaTemplate.SEGUNDA,
                        ParcelaTemplate.TERCEIRA);
        Fatura fatura = Fixture.from(Fatura.class)
                .gimme(FaturaTemplate.VALIDO);
        FaturaRequest request = Fixture.from(FaturaRequest.class)
                .gimme(FaturaRequestTemplate.VALIDO);

        Mockito.when(cartaoCreditoService.buscarPorId(anyLong())).thenReturn(cartaoCredito);
        Mockito.when(parcelaService.buscarParcelasParaGerarFatura(any(LocalDate.class), any(ContaBancaria.class)))
                .thenReturn(parcelas);
        Mockito.when(faturaService.gerar(any(Fatura.class))).thenReturn(fatura);
        Mockito.when(cartaoCreditoService.atualizar(anyLong(), any(CartaoCredito.class), any(ContaBancaria.class)))
                .thenReturn(cartaoCredito);

        Fatura faturaGerada = faturaFacade.gerarFatura(request);

        assertNotNull(faturaGerada);
    }

    @Test
    public void deveAtualizarFatura() {
        CartaoCredito cartaoCredito = Fixture.from(CartaoCredito.class)
                .gimme(CartaoCreditoTemplate.COM_CONTA_BANCARIA);
        List<Parcela> parcelas = Fixture.from(Parcela.class)
                .gimme(3,
                        ParcelaTemplate.PRIMEIRA,
                        ParcelaTemplate.SEGUNDA,
                        ParcelaTemplate.TERCEIRA);
        Fatura fatura = Fixture.from(Fatura.class)
                .gimme(FaturaTemplate.VALIDO);
        FaturaRequest request = Fixture.from(FaturaRequest.class)
                .gimme(FaturaRequestTemplate.VALIDO);
        final Long idFatura = 1L;

        Mockito.when(faturaService.buscarPorId(anyLong())).thenReturn(fatura);
        Mockito.when(cartaoCreditoService.buscarPorId(anyLong())).thenReturn(cartaoCredito);
        Mockito.when(parcelaService.buscarParcelasParaGerarFatura(any(LocalDate.class), any(ContaBancaria.class)))
                .thenReturn(parcelas);

        faturaFacade.atualizarFatura(idFatura, request);
    }


    @Test
    public void deveBuscarFaturaPorId() {
        Fatura fatura = Fixture.from(Fatura.class)
                .gimme(FaturaTemplate.VALIDO);
        final Long idFatura = 1L;

        Mockito.when(faturaService.buscarPorId(anyLong())).thenReturn(fatura);

        FaturaResponse response = faturaFacade.buscarPorId(idFatura);

        assertNotNull(response);
    }

    @Test
    public void deveListarFaturas() {
        CartaoCredito cartaoCredito = Fixture.from(CartaoCredito.class)
                .gimme(CartaoCreditoTemplate.COM_CONTA_BANCARIA);
        List<FaturaResponse> faturasResponse = Fixture.from(FaturaResponse.class)
                .gimme(1, FaturaResponseTemplate.VALIDO);
        final Long idCartao = 1L;
        final int pagina = 0;
        final int tamanho = 10;

        FaturasPagination faturasPagination =
                new FaturasPagination(faturasResponse);
        Pageable paginacao = PageRequest.of(pagina, tamanho);

        Mockito.when(cartaoCreditoService.buscarPorId(anyLong()))
                .thenReturn(cartaoCredito);
        Mockito.when(faturaService.listar(anyLong(), any(Pageable.class)))
                .thenReturn(faturasPagination);

        FaturasPagination faturasPaginadas = faturaFacade.listar(idCartao, paginacao);

        assertNotNull(faturasPaginadas);
    }
}
