package com.savemoney.templates.requests;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.savemoney.domain.models.Transacao;
import com.savemoney.domain.requests.ContaBancariaRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UsuarioRequestTemplate implements TemplateLoader {

    public static final String ENTRADA = "entrada";
    public static final String SAIDA = "saida";

    @Override
    public void load() {
        Fixture.of(Transacao.class).addTemplate(ENTRADA, new Rule() {{
            String senha = new BCryptPasswordEncoder().encode("1234");

            add("email", "admin@email.com");
            add("senha", senha);
            add("contaBancaria", one(ContaBancariaRequest.class,
                    ContaBancariaRequestTemplate.VALIDO));
        }});
    }
}
