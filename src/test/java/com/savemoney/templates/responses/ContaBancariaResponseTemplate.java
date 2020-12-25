package com.savemoney.templates.responses;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.savemoney.domain.models.ContaBancaria;
import com.savemoney.domain.responses.BancoResponse;

import java.math.BigDecimal;

public class ContaBancariaResponseTemplate implements TemplateLoader {

    public static final String VALIDO = "valido";

    @Override
    public void load() {
        Fixture.of(ContaBancaria.class).addTemplate(VALIDO, new Rule() {{
            add("idContaBancaria", "1");
            add("agencia", "0001");
            add("conta", "123456");
            add("saldo", new BigDecimal("1500"));
            add("banco", one(BancoResponse.class,
                    BancoResponseTemplate.VALIDO));
        }});
    }
}
