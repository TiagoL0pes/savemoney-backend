package com.savemoney.templates.models;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.savemoney.domain.enums.StatusPagamento;
import com.savemoney.domain.enums.TipoTransacao;
import com.savemoney.domain.models.ContaBancaria;
import com.savemoney.domain.models.Despesa;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DespesaTemplate implements TemplateLoader {

    public static final String PENDENTE = "pendente";
    public static final String PAGO = "pendente";

    @Override
    public void load() {
        Fixture.of(Despesa.class).addTemplate(PENDENTE, new Rule() {{
            add("idDespesa", "1");
            add("dataEntrada", LocalDate.of(2020, 7, 15));
            add("dataPagamento", null);
            add("dataVencimento", LocalDate.of(2020, 7, 30));
            add("descricao", "Camiseta");
            add("valor", new BigDecimal("50"));
            add("statusPagamento", StatusPagamento.PENDENTE);
            add("tipoTransacao", TipoTransacao.SAIDA);
            add("contaBancaria", one(ContaBancaria.class,
                    ContaBancariaTemplate.VALIDO));
        }}).addTemplate(PAGO, new Rule() {{
            add("idDespesa", "1");
            add("dataEntrada", LocalDate.of(2020, 7, 15));
            add("dataPagamento", LocalDate.of(2020, 7, 20));
            add("dataVencimento", LocalDate.of(2020, 7, 30));
            add("descricao", "Camiseta");
            add("valor", new BigDecimal("50"));
            add("statusPagamento", StatusPagamento.PAGO);
            add("tipoTransacao", TipoTransacao.SAIDA);
            add("contaBancaria", one(ContaBancaria.class,
                    ContaBancariaTemplate.VALIDO));
        }});
    }
}
