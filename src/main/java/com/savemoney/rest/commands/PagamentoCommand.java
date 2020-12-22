package com.savemoney.rest.commands;

import com.savemoney.domain.models.ContaBancaria;
import com.savemoney.domain.models.Despesa;

public interface PagamentoCommand {

    void execute(Despesa despesa, ContaBancaria contaBancaria);
}
