package com.savemoney.templates.models;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.savemoney.domain.enums.StatusPagamento;
import com.savemoney.domain.models.Fatura;
import com.savemoney.domain.models.Parcela;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FaturaTemplate implements TemplateLoader {

    public static final String PENDENTE = "pendente";
    public static final String PAGO = "pago";

    @Override
    public void load() {
        Fixture.of(Fatura.class).addTemplate(PENDENTE, new Rule() {{
            add("idFatura", "1");
            add("dataVencimento", LocalDate.of(2020, 1, 10));
            add("statusPagamento", StatusPagamento.PENDENTE);
            add("total", new BigDecimal("50"));
            add("parcelas", has(3).of(Parcela.class,
                    ParcelaTemplate.PRIMEIRA,
                    ParcelaTemplate.SEGUNDA,
                    ParcelaTemplate.TERCEIRA));
        }}).addTemplate(PAGO, new Rule() {{
            add("idFatura", "1");
            add("dataVencimento", LocalDate.of(2020, 1, 10));
            add("statusPagamento", StatusPagamento.PAGO);
            add("total", new BigDecimal("50"));
            add("parcelas", has(3).of(Parcela.class,
                    ParcelaTemplate.PRIMEIRA,
                    ParcelaTemplate.SEGUNDA,
                    ParcelaTemplate.TERCEIRA));
        }});
    }
}
