package com.savemoney.templates.models;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.savemoney.domain.models.ContaBancaria;
import com.savemoney.domain.models.Transacao;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UsuarioTemplate implements TemplateLoader {

    public static final String VALIDO = "valido";

    @Override
    public void load() {
        Fixture.of(Transacao.class).addTemplate(VALIDO, new Rule() {{
            String senha = new BCryptPasswordEncoder().encode("1234");

            add("idUsuario", "1");
            add("email", "admin@email.com");
            add("senha", senha);
            add("contaNaoExpirada", true);
            add("contaNaoBloqueada", true);
            add("credenciaisNaoExpiradas", true);
            add("habilitado", true);
            add("contaBancaria", one(ContaBancaria.class,
                    ContaBancariaTemplate.VALIDO));
        }});
    }
}
