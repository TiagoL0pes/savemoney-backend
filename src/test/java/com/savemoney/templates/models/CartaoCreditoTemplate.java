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
    public static final String COM_CONTA_BANCARIA = "com_conta_bancaria";
    public static final String SEM_PARCELAS = "sem_parcelas";

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
                    .of(Fatura.class, FaturaTemplate.PENDENTE));
        }}).addTemplate(COM_CONTA_BANCARIA, new Rule() {{
            add("idCartaoCredito", "1");
            add("numero", "1234567812345678");
            add("diaVencimento", "10");
            add("limiteCredito", new BigDecimal("2500"));
            add("limiteUtilizado", BigDecimal.ZERO);
            add("contaBancaria", one(ContaBancaria.class,
                    ContaBancariaTemplate.SEM_CARTAO_CREDITO));
            add("itens", has(1)
                    .of(ItemCartao.class, ItemCartaoTemplate.VALIDO));
            add("faturas", has(1)
                    .of(Fatura.class, FaturaTemplate.PENDENTE));
        }}).addTemplate(SEM_PARCELAS, new Rule() {{
            add("idCartaoCredito", "1");
            add("numero", "1234567812345678");
            add("diaVencimento", "10");
            add("limiteCredito", new BigDecimal("2500"));
            add("limiteUtilizado", BigDecimal.ZERO);
            add("itens", has(1)
                    .of(ItemCartao.class, ItemCartaoTemplate.SEM_PARCELAS));
            add("faturas", has(1)
                    .of(Fatura.class, FaturaTemplate.PENDENTE));
        }});
    }
}
