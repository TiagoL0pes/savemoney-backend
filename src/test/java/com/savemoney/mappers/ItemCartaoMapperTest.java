package com.savemoney.mappers;

import br.com.six2six.fixturefactory.Fixture;
import com.savemoney.domain.mappers.ItemCartaoMapper;
import com.savemoney.domain.models.ItemCartao;
import com.savemoney.domain.requests.ItemCartaoRequest;
import com.savemoney.templates.requests.ItemCartaoRequestTemplate;
import com.savemoney.utils.helpers.DateHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ItemCartaoMapperTest {

    private final ItemCartaoMapper mapper =
            Mappers.getMapper(ItemCartaoMapper.class);

    @BeforeEach
    public void setup() {
        loadTemplates("com.savemoney.templates");
    }

    @Test
    public void itemCartaoRequestToItemCartao() {
        ItemCartaoRequest request = Fixture.from(ItemCartaoRequest.class)
                .gimme(ItemCartaoRequestTemplate.VALIDO);
        request.setNumeroParcelas(9);
        final Integer diaVencimento = 10;

        ItemCartao itemCartao = mapper.toItemCartao(request, diaVencimento);

        assertNotNull(itemCartao);
        assertEquals(DateHelper.toLocalDate(request.getDataCompra()), itemCartao.getDataCompra());
        assertEquals(request.getDescricao(), itemCartao.getDescricao());
        assertEquals(request.getValorTotal(), itemCartao.getValorTotal());
        assertEquals(request.getNumeroParcelas(), itemCartao.getNumeroParcelas());
    }
}
