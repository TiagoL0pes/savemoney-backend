package com.savemoney.templates.responses;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.savemoney.domain.responses.UsuarioResponse;

public class TokenResponseTemplate implements TemplateLoader {

    public static final String VALIDO = "valido";

    @Override
    public void load() {
        Fixture.of(UsuarioResponse.class).addTemplate(VALIDO, new Rule() {{
            add("token", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ7XCJpZENvbnRhQmFuY2FyaWFcIjoxLFwiZW1haWxcIjpcImFkbWluQGVtYWlsLmNvbVwiLFwiYWdlbmNpYVwiOlwiMDAwMVwiLFwiY29udGFcIjpcIjEyMzQ1NlwifSIsImV4cCI6MTYwODg1ODAzMn0.jUCXRnEbioeMgo1-hioDlOiXyWk9ercBPhsuAnt6JBseDuHzaIIgZ4kJq2TUd_tMsp0lCojyXFYq0y9cHj7hfQ");
        }});
    }
}
