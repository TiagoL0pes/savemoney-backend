package com.savemoney.templates.requests;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.savemoney.domain.requests.ItemCartaoRequest;

import java.math.BigDecimal;

public class ItemCartaoRequestTemplate implements TemplateLoader {

    public static final String VALIDO = "valido";

    @Override
    public void load() {
        Fixture.of(ItemCartaoRequest.class).addTemplate(VALIDO, new Rule() {{
            add("dataCompra", "07/01/2020");
            add("descricao", "Tênis");
            add("valorTotal", new BigDecimal("150.00"));
            add("numeroParcelas", "3");
        }});
    }
}