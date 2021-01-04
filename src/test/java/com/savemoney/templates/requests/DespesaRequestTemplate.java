package com.savemoney.templates.requests;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.savemoney.domain.requests.DespesaRequest;

import java.math.BigDecimal;

public class DespesaRequestTemplate implements TemplateLoader {

    public static final String VALIDO = "valido";
    public static final String SEM_DATA_ENTRADA_E_VENCIMENTO = "sem_data_entrada_e_vencimento";

    @Override
    public void load() {
        Fixture.of(DespesaRequest.class).addTemplate(VALIDO, new Rule() {{
            add("dataEntrada", "15/07/2020");
            add("dataVencimento", "30/07/2020");
            add("descricao", "Camiseta");
            add("valor", new BigDecimal("50"));
        }}).addTemplate(SEM_DATA_ENTRADA_E_VENCIMENTO, new Rule() {{
            add("dataEntrada", "");
            add("dataVencimento", "");
            add("descricao", "Camiseta");
            add("valor", new BigDecimal("50"));
        }});
    }
}
