package com.savemoney.templates.models;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.savemoney.domain.models.ItemCartao;
import com.savemoney.domain.models.Parcela;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ItemCartaoTemplate implements TemplateLoader {

    public static final String VALIDO = "valido";
    public static final String SEM_PARCELAS = "sem_parcelas";

    @Override
    public void load() {
        Fixture.of(ItemCartao.class).addTemplate(VALIDO, new Rule() {{
            add("idItemCartao", "1");
            add("dataCompra", LocalDate.of(2020, 1, 7));
            add("descricao", "Tênis");
            add("valorTotal", new BigDecimal("150"));
            add("numeroParcelas", "3");
            add("parcelas", has(3).of(Parcela.class,
                    ParcelaTemplate.PRIMEIRA,
                    ParcelaTemplate.SEGUNDA,
                    ParcelaTemplate.TERCEIRA));
        }}).addTemplate(SEM_PARCELAS, new Rule() {{
            add("idItemCartao", "1");
            add("dataCompra", LocalDate.of(2020, 1, 7));
            add("descricao", "Tênis");
            add("valorTotal", new BigDecimal("150"));
            add("numeroParcelas", "3");
        }});
    }
}
