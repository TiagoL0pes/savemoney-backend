package com.savemoney.templates.models;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.savemoney.domain.models.CartaoCredito;
import com.savemoney.domain.models.ContaBancaria;
import com.savemoney.domain.models.Fatura;
import com.savemoney.domain.models.ItemCartao;

import java.math.BigDecimal;

public class CartaoCreditoTemplate implements TemplateLoader {

    public static final String VALIDO = "valido";

    @Override
    public void load() {
        Fixture.of(CartaoCredito.class).addTemplate(VALIDO, new Rule() {{
            add("idCartaoCredito", "1");
            add("numero", "1234567812345678");
            add("diaVencimento", "10");
            add("limiteCredito", new BigDecimal("2500"));
            add("limiteUtilizado", BigDecimal.ZERO);
            add("itens", has(1)
                    .of(ItemCartao.class, ItemCartaoTemplate.VALIDO));
            add("faturas", has(1)
                    .of(Fatura.class, FaturaTemplate.VALIDO));
        }});
    }
}
