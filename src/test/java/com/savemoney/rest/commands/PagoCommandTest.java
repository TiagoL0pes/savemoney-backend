package com.savemoney.rest.commands;

import br.com.six2six.fixturefactory.Fixture;
import com.savemoney.domain.models.ContaBancaria;
import com.savemoney.domain.models.Despesa;
import com.savemoney.templates.models.ContaBancariaTemplate;
import com.savemoney.templates.models.DespesaTemplate;
import com.savemoney.utils.exceptions.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class PagoCommandTest {

    @InjectMocks
    private PagoCommand command;

    @BeforeEach
    public void setup() {
        loadTemplates("com.savemoney.templates");
    }

    @Test
    public void deveLancarExcecaoQuandoStatusDespesaPago() {
        Despesa despesa = Fixture.from(Despesa.class)
                .gimme(DespesaTemplate.PAGO);
        ContaBancaria contaBancaria = Fixture.from(ContaBancaria.class)
                .gimme(ContaBancariaTemplate.VALIDO);

        assertThrows(BadRequestException.class, () -> {
            command.execute(despesa, contaBancaria);
        });

        despesa.setDescricao(null);
        assertThrows(BadRequestException.class, () -> {
            command.execute(despesa, contaBancaria);
        });
    }

}
