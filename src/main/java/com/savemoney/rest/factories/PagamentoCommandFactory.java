package com.savemoney.rest.factories;

import com.savemoney.configs.PagamentoCommandConfig;
import com.savemoney.domain.enums.StatusPagamento;
import com.savemoney.rest.commands.PagamentoCommand;
import com.savemoney.rest.commands.PagoCommand;
import com.savemoney.rest.commands.PendenteCommand;
import com.savemoney.utils.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PagamentoCommandFactory {

    @Autowired
    private PendenteCommand pendenteCommand;

    @Autowired
    private PagoCommand pagoCommand;

    @Autowired
    private PagamentoCommandConfig pagamentoCommandConfig;

    public PagamentoCommand getCommand(StatusPagamento statusPagamento) {
        PagamentoCommand paymentCommand = pagamentoCommandConfig.getCommands().get(statusPagamento);
        return validarCommand(paymentCommand);
    }

    private PagamentoCommand validarCommand(PagamentoCommand pagamentoCommand) {
        if (Objects.isNull(pagamentoCommand)) {
            throw new BadRequestException("Status de pagamento inválido");
        }
        return pagamentoCommand;
    }

}
