package com.savemoney.abstracts;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public abstract class AbstractService {

    protected String validaTexto(String textoAtual, String novoTexto) {
        return StringUtils.isNotBlank(novoTexto.trim()) ? novoTexto : textoAtual;
    }

    protected BigDecimal validaNumero(BigDecimal numeroAtual, BigDecimal novoNumero) {
        return Objects.isNull(novoNumero) ? numeroAtual : novoNumero.setScale(5, RoundingMode.HALF_EVEN);
    }

    protected Integer validaNumero(Integer numeroAtual, Integer novoNumero) {
        return Objects.isNull(novoNumero) ? numeroAtual : novoNumero;
    }
}
