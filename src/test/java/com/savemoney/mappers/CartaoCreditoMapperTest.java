package com.savemoney.mappers;

import br.com.six2six.fixturefactory.Fixture;
import com.savemoney.domain.mappers.CartaoCreditoMapper;
import com.savemoney.domain.models.CartaoCredito;
import com.savemoney.domain.models.ItemCartao;
import com.savemoney.domain.requests.CartaoCreditoRequest;
import com.savemoney.domain.responses.CartaoCreditoResponse;
import com.savemoney.domain.responses.ItemCartaoResponse;
import com.savemoney.domain.responses.ResumoItemCartaoResponse;
import com.savemoney.templates.models.CartaoCreditoTemplate;
import com.savemoney.templates.models.ItemCartaoTemplate;
import com.savemoney.templates.requests.CartaoCreditoRequestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CartaoCreditoMapperTest {

    private final CartaoCreditoMapper mapper =
            Mappers.getMapper(CartaoCreditoMapper.class);

    @BeforeEach
    public void setup() {
        loadTemplates("com.savemoney.templates");
    }

    @Test
    public void cartaoCreditoToResumoItemCartaoResponse() {
        CartaoCredito cartaoCredito = Fixture.from(CartaoCredito.class)
                .gimme(CartaoCreditoTemplate.VALIDO);

        ResumoItemCartaoResponse resumo =
                mapper.toResumoItemCartaoResponse(cartaoCredito);

        assertNotNull(resumo);
    }

    @Test
    public void itemCartaoToItemCartaoResponse() {
        ItemCartao itemCartao = Fixture.from(ItemCartao.class)
                .gimme(ItemCartaoTemplate.VALIDO);

        ItemCartaoResponse response = mapper.toItemCartaoResponse(itemCartao);

        assertNotNull(response);
    }

    @Test
    public void cartaoCreditoToCartaoCreditoResponse() {
        CartaoCredito cartaoCredito = Fixture.from(CartaoCredito.class)
                .gimme(CartaoCreditoTemplate.VALIDO);

        CartaoCreditoResponse response = mapper.toCartaoCreditoResponse(cartaoCredito);

        assertNotNull(response);
    }

    @Test
    public void cartoesCreditoToCartoesCreditoResponse() {
        List<CartaoCredito> cartoesCredito = Fixture.from(CartaoCredito.class)
                .gimme(1, CartaoCreditoTemplate.VALIDO);

        List<CartaoCreditoResponse> responses =
                mapper.toCartoesCreditoResponse(cartoesCredito);

        assertNotNull(responses);
    }

    @Test
    public void cartaoCreditoRequestToCartaoCredito() {
        CartaoCreditoRequest request = Fixture.from(CartaoCreditoRequest.class)
                .gimme(CartaoCreditoRequestTemplate.VALIDO);
        
        CartaoCredito cartaoCredito = mapper.toCartaoCredito(request);

        assertNotNull(cartaoCredito);
    }
}
