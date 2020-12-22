package com.savemoney.rest.commands;

import com.savemoney.domain.models.ContaBancaria;
import com.savemoney.domain.models.Despesa;
import com.savemoney.utils.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class PagoCommand implements PagamentoCommand {

    @Override
    public void execute(Despesa despesa, ContaBancaria contaBancaria) {
        throw new BadRequestException("Despesa " + despesa.getDescricao() + " já está paga");
    }
}
