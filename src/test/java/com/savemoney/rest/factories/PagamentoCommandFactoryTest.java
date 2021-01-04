package com.savemoney.rest.factories;

import com.savemoney.configs.PagamentoCommandConfig;
import com.savemoney.domain.enums.StatusPagamento;
import com.savemoney.rest.commands.PagamentoCommand;
import com.savemoney.rest.commands.PagoCommand;
import com.savemoney.rest.commands.PendenteCommand;
import com.savemoney.utils.exceptions.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates;
import static com.savemoney.domain.enums.StatusPagamento.PENDENTE;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class PagamentoCommandFactoryTest {

    @Mock
    private PendenteCommand pendenteCommand;

    @Mock
    private PagoCommand pagoCommand;

    @Mock
    private PagamentoCommandConfig config;

    @InjectMocks
    private PagamentoCommandFactory factory;

    @BeforeEach
    public void setup() {
        loadTemplates("com.savemoney.templates");
    }

    @Test
    public void deveValidarComando() {
        Mockito.when(config.getCommands())
                .thenReturn(getCommands());
        PagamentoCommand command = factory.getCommand(PENDENTE);

        assertNotNull(command);
    }

    @Test
    public void deveLancarExcecaoQuandoComandoInvalido() {
        Mockito.when(config.getCommands())
                .thenReturn(getCommands());

        assertThrows(BadRequestException.class, () -> {
            factory.getCommand(null);
        });
    }

    private Map<StatusPagamento, PagamentoCommand> getCommands() {
        Map<StatusPagamento, PagamentoCommand> commands = new HashMap<>();
        commands.put(StatusPagamento.PENDENTE, pendenteCommand);
        commands.put(StatusPagamento.PAGO, pagoCommand);

        return commands;
    }

}
