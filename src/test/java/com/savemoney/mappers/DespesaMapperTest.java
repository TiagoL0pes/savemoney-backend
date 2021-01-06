package com.savemoney.mappers;

import br.com.six2six.fixturefactory.Fixture;
import com.savemoney.domain.mappers.DespesaMapper;
import com.savemoney.domain.models.Despesa;
import com.savemoney.domain.models.Fatura;
import com.savemoney.domain.models.Parcela;
import com.savemoney.domain.requests.DespesaRequest;
import com.savemoney.domain.responses.DespesaResponse;
import com.savemoney.templates.models.DespesaTemplate;
import com.savemoney.templates.models.FaturaTemplate;
import com.savemoney.templates.models.ParcelaTemplate;
import com.savemoney.templates.requests.DespesaRequestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DespesaMapperTest {

    private final DespesaMapper mapper =
            Mappers.getMapper(DespesaMapper.class);

    @BeforeEach
    public void setup() {
        loadTemplates("com.savemoney.templates");
    }

    @Test
    public void despesaRequestToDespesa() {
        DespesaRequest request = Fixture.from(DespesaRequest.class)
                .gimme(DespesaRequestTemplate.SEM_DATA_ENTRADA_E_VENCIMENTO);

        Despesa despesa = mapper.toDespesa(request);

        assertNotNull(despesa);
    }

    @Test
    public void parcelaToDespesa() {
        Parcela parcela = Fixture.from(Parcela.class)
                .gimme(ParcelaTemplate.PRIMEIRA);
        parcela.setValor(null);

        Despesa despesa = mapper.toDespesa(parcela);

        assertNotNull(despesa);
    }

    @Test
    public void faturaToDespesa() {
        Fatura fatura = Fixture.from(Fatura.class)
                .gimme(FaturaTemplate.PENDENTE);

        Despesa despesa = mapper.toDespesa(fatura);

        assertNotNull(despesa);
    }

    @Test
    public void despesaToDespesaResponse() {
        Despesa despesa = Fixture.from(Despesa.class)
                .gimme(DespesaTemplate.PENDENTE);

        DespesaResponse response = mapper.toDespesaResponse(despesa);

        assertNotNull(response);
    }

    @Test
    public void despesasToDespesasResponse() {
        List<Despesa> despesas = Fixture.from(Despesa.class)
                .gimme(1, DespesaTemplate.PENDENTE);

        List<DespesaResponse> responses = mapper.toDespesasResponse(despesas);

        assertNotNull(responses);
    }
}
