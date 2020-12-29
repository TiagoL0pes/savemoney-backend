package com.savemoney.templates.requests;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.savemoney.domain.requests.ContaBancariaRequest;

import java.math.BigDecimal;

public class ContaBancariaRequestTemplate implements TemplateLoader {

    public static final String VALIDO = "valido";

    @Override
    public void load() {
        Fixture.of(ContaBancariaRequest.class).addTemplate(VALIDO, new Rule() {{
            add("idBanco", "1");
            add("agencia", "0001");
            add("conta", "123456");
            add("saldo", new BigDecimal("1500"));
        }});
    }
}
