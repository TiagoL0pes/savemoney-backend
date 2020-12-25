package com.savemoney.templates.responses;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.savemoney.domain.enums.StatusPagamento;
import com.savemoney.domain.models.Fatura;
import com.savemoney.domain.responses.ParcelaResponse;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FaturaResponseTemplate implements TemplateLoader {

    public static final String VALIDO = "valido";

    @Override
    public void load() {
        Fixture.of(Fatura.class).addTemplate(VALIDO, new Rule() {{
            add("idFatura", "1");
            add("dataVencimento", LocalDate.of(2020, 1, 10));
            add("statusPagamento", StatusPagamento.PENDENTE);
            add("total", new BigDecimal("50"));
            add("parcelas", has(3).of(ParcelaResponse.class,
                    ParcelaResponseTemplate.PRIMEIRA,
                    ParcelaResponseTemplate.SEGUNDA,
                    ParcelaResponseTemplate.TERCEIRA));
        }});
    }
}
