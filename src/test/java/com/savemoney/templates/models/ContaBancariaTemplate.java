package com.savemoney.templates.models;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.savemoney.domain.models.Banco;
import com.savemoney.domain.models.CartaoCredito;
import com.savemoney.domain.models.ContaBancaria;
import com.savemoney.domain.models.Transacao;
import com.savemoney.security.domain.models.Usuario;

import java.math.BigDecimal;

public class ContaBancariaTemplate implements TemplateLoader {

    public static final String VALIDO = "valido";

    @Override
    public void load() {
        Fixture.of(ContaBancaria.class).addTemplate(VALIDO, new Rule() {{
            add("idContaBancaria", "1");
            add("agencia", "0001");
            add("conta", "123456");
            add("saldo", new BigDecimal("1500"));
            add("banco", one(Banco.class,
                    BancoTemplate.VALIDO));
            add("transacoes", has(1)
                    .of(Transacao.class, TransacaoTemplate.ENTRADA));
            add("cartoesCredito", has(1)
                    .of(CartaoCredito.class, CartaoCreditoTemplate.VALIDO));
            add("usuario", one(Usuario.class,
                    UsuarioTemplate.VALIDO));
        }});
    }
}
