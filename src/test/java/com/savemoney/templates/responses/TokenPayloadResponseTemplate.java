package com.savemoney.templates.responses;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.savemoney.domain.responses.UsuarioResponse;

public class TokenPayloadResponseTemplate implements TemplateLoader {

    public static final String VALIDO = "valido";

    @Override
    public void load() {
        Fixture.of(UsuarioResponse.class).addTemplate(VALIDO, new Rule() {{
            add("idContaBancaria", "1");
            add("email", "admin@email.com");
            add("agencia", "0001");
            add("conta", "123456");
        }});
    }
}
