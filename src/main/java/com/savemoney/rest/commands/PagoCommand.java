package com.savemoney.rest.commands;

import com.savemoney.domain.models.ContaBancaria;
import com.savemoney.domain.models.Despesa;
import com.savemoney.utils.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PagoCommand implements PagamentoCommand {

    @Override
    public void execute(Despesa despesa, ContaBancaria contaBancaria) {
        String descricao = Objects.nonNull(despesa.getDescricao()) ? despesa.getDescricao() : "";
        throw new BadRequestException("Despesa " + descricao + " já está paga");
    }
}
