package com.savemoney.templates.requests;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.savemoney.domain.requests.FaturaRequest;

public class FaturaRequestTemplate implements TemplateLoader {

    public static final String VALIDO = "valido";
    public static final String SEM_MES_ANO = "sem_mes_ano";
    public static final String COM_MES = "com_mes";

    @Override
    public void load() {
        Fixture.of(FaturaRequest.class).addTemplate(VALIDO, new Rule() {{
            add("idCartao", "1");
            add("mes", "11");
            add("ano", "2020");
        }}).addTemplate(SEM_MES_ANO, new Rule() {{
            add("idCartao", "1");
        }}).addTemplate(COM_MES, new Rule() {{
            add("idCartao", "1");
            add("mes", "11");
        }});;
    }
}