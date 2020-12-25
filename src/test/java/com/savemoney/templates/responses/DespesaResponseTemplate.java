package com.savemoney.templates.responses;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.savemoney.domain.enums.StatusPagamento;
import com.savemoney.domain.enums.TipoTransacao;
import com.savemoney.domain.models.Despesa;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DespesaResponseTemplate implements TemplateLoader {

    public static final String VALIDO = "valido";

    @Override
    public void load() {
        Fixture.of(Despesa.class).addTemplate(VALIDO, new Rule() {{
            add("idDespesa", "1");
            add("dataEntrada", LocalDate.of(2020, 7, 15));
            add("dataPagamento", null);
            add("dataVencimento", LocalDate.of(2020, 7, 30));
            add("descricao", "Camiseta");
            add("valor", new BigDecimal("50"));
            add("statusPagamento", StatusPagamento.PENDENTE);
            add("tipoTransacao", TipoTransacao.SAIDA);
        }});
    }
}
