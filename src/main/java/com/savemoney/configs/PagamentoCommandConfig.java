package com.savemoney.configs;

import com.savemoney.domain.enums.StatusPagamento;
import com.savemoney.rest.commands.PagamentoCommand;
import com.savemoney.rest.commands.PagoCommand;
import com.savemoney.rest.commands.PendenteCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class PagamentoCommandConfig {

    @Autowired
    private PendenteCommand pendenteCommand;

    @Autowired
    private PagoCommand pagoCommand;

    private Map<StatusPagamento, PagamentoCommand> pagamentoCommands;

    @Bean
    public Map<StatusPagamento, PagamentoCommand> getCommands() {
        if (CollectionUtils.isEmpty(pagamentoCommands)) {
            pagamentoCommands = new HashMap<>();
            pagamentoCommands.put(StatusPagamento.PENDENTE, pendenteCommand);
            pagamentoCommands.put(StatusPagamento.PAGO, pagoCommand);
        }

        return pagamentoCommands;
    }
}
