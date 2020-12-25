package com.savemoney.templates.responses;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.savemoney.domain.models.CartaoCredito;

import java.math.BigDecimal;

public class CartaoCreditoResponseTemplate implements TemplateLoader {

    public static final String VALIDO = "valido";

    @Override
    public void load() {
        Fixture.of(CartaoCredito.class).addTemplate(VALIDO, new Rule() {{
            add("idCartaoCredito", "1");
            add("numero", "1234567812345678");
            add("diaVencimento", "10");
            add("limiteCredito", new BigDecimal("2500"));
            add("limiteUtilizado", BigDecimal.ZERO);
            add("limiteDisponivel", new BigDecimal("2500"));
        }});
    }

}
