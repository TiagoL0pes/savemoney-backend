package com.savemoney.templates.responses;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.savemoney.domain.responses.ItemCartaoResponse;
import com.savemoney.domain.responses.ResumoItemCartaoResponse;

import java.math.BigDecimal;

public class ResumoItemCartaoResponseTemplate implements TemplateLoader {

    public static final String VALIDO = "valido";

    @Override
    public void load() {
        Fixture.of(ResumoItemCartaoResponse.class).addTemplate(VALIDO, new Rule() {{
            add("idCartaoCredito", "1");
            add("totalItens", "1");
            add("valorTotal", new BigDecimal("150"));
            add("itens", has(1)
                    .of(ItemCartaoResponse.class, ItemCartaoResponseTemplate.VALIDO));
        }});
    }
}
