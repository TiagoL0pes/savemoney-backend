package com.savemoney.templates.models;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.savemoney.domain.enums.Mes;
import com.savemoney.domain.enums.StatusPagamento;
import com.savemoney.domain.models.Parcela;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ParcelaTemplate implements TemplateLoader {

    public static final String PRIMEIRA = "valido";
    public static final String SEGUNDA = "segunda";
    public static final String TERCEIRA = "terceira";

    @Override
    public void load() {
        Fixture.of(Parcela.class).addTemplate(PRIMEIRA, new Rule() {{
            add("idParcela", "1");
            add("numeroParcela", "1");
            add("valor", new BigDecimal("50"));
            add("descricao", "Tênis - 1/3");
            add("dataPagamento", null);
            add("dataVencimento", LocalDate.of(2020, 1, 7));
            add("mesVencimento", Mes.JANEIRO);
            add("statusPagamento", StatusPagamento.PENDENTE);
        }}).addTemplate(SEGUNDA, new Rule() {{
            add("idParcela", "2");
            add("numeroParcela", "2");
            add("valor", new BigDecimal("50"));
            add("descricao", "Tênis - 2/3");
            add("dataPagamento", null);
            add("dataVencimento", LocalDate.of(2020, 2, 7));
            add("mesVencimento", Mes.FEVEREIRO);
            add("statusPagamento", StatusPagamento.PENDENTE);
        }}).addTemplate(TERCEIRA, new Rule() {{
            add("idParcela", "3");
            add("numeroParcela", "3");
            add("valor", new BigDecimal("50"));
            add("descricao", "Tênis - 3/3");
            add("dataPagamento", null);
            add("dataVencimento", LocalDate.of(2020, 3, 7));
            add("mesVencimento", Mes.MARCO);
            add("statusPagamento", StatusPagamento.PENDENTE);
        }});
    }
}
