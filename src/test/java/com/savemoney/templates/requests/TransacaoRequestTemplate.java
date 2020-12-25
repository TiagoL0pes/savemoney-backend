package com.savemoney.templates.requests;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.savemoney.domain.enums.TipoTransacao;
import com.savemoney.domain.models.Transacao;

import java.math.BigDecimal;

public class TransacaoRequestTemplate implements TemplateLoader {

    public static final String ENTRADA = "entrada";
    public static final String SAIDA = "saida";

    @Override
    public void load() {
        Fixture.of(Transacao.class).addTemplate(ENTRADA, new Rule() {{
            add("valor", new BigDecimal("100"));
            add("descricao", "Depósito");
            add("tipoTransacao", TipoTransacao.ENTRADA);
        }}).addTemplate(SAIDA, new Rule() {{
            add("valor", new BigDecimal("100"));
            add("descricao", "Depósito");
            add("tipoTransacao", TipoTransacao.SAIDA);
        }});
    }
}
