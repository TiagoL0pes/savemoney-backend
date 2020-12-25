package com.savemoney.templates.models;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.savemoney.domain.models.Banco;
import com.savemoney.domain.models.ContaBancaria;

public class BancoTemplate implements TemplateLoader {

    public static final String VALIDO = "valido";

    @Override
    public void load() {
        Fixture.of(Banco.class).addTemplate(VALIDO, new Rule() {{
            add("idBanco", "1");
            add("codigo", "0001");
            add("nome", "123456");
            add("contasBancarias", has(1)
                    .of(ContaBancaria.class, ContaBancariaTemplate.VALIDO));
        }});
    }
}
