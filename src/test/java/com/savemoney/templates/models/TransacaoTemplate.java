package com.savemoney.templates.models;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.savemoney.domain.enums.TipoTransacao;
import com.savemoney.domain.models.ContaBancaria;
import com.savemoney.domain.models.Transacao;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransacaoTemplate implements TemplateLoader {

    public static final String ENTRADA = "entrada";
    public static final String SAIDA = "saida";

    @Override
    public void load() {
        Fixture.of(Transacao.class).addTemplate(ENTRADA, new Rule() {{
            add("idTransacao", "1");
            add("dataEntrada", LocalDate.of(2020, 7, 12));
            add("descricao", "Depósito");
            add("valor", new BigDecimal("100"));
            add("tipoTransacao", TipoTransacao.ENTRADA);
            add("contaBancaria", one(ContaBancaria.class,
                    ContaBancariaTemplate.VALIDO));
        }}).addTemplate(SAIDA, new Rule() {{
            add("idTransacao", "1");
            add("dataEntrada", LocalDate.of(2020, 7, 12));
            add("descricao", "Saque");
            add("valor", new BigDecimal("100"));
            add("tipoTransacao", TipoTransacao.SAIDA);
            add("contaBancaria", one(ContaBancaria.class,
                    ContaBancariaTemplate.VALIDO));
        }});
    }
}
