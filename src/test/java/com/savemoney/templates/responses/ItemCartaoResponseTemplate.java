package com.savemoney.templates.responses;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.savemoney.domain.models.ItemCartao;
import com.savemoney.domain.responses.ParcelaResponse;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ItemCartaoResponseTemplate implements TemplateLoader {

    public static final String VALIDO = "valido";

    @Override
    public void load() {
        Fixture.of(ItemCartao.class).addTemplate(VALIDO, new Rule() {{
            add("idItemCartao", "1");
            add("dataCompra", LocalDate.of(2020, 1, 7));
            add("descricao", "Tênis");
            add("valorTotal", new BigDecimal("150"));
            add("numeroParcelas", "3");
            add("parcelas", has(3).of(ParcelaResponse.class,
                    ParcelaResponseTemplate.PRIMEIRA,
                    ParcelaResponseTemplate.SEGUNDA,
                    ParcelaResponseTemplate.TERCEIRA));
        }});
    }
}
