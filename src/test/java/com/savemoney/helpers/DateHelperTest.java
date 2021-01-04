package com.savemoney.helpers;

import com.savemoney.utils.helpers.DateHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DateHelperTest {

    @BeforeEach
    public void setup() {
        loadTemplates("com.savemoney.templates");
    }

    @Test
    public void deveFormatarData() {
        LocalDate hoje = LocalDate.now();

        String dataFormatada = DateHelper.formatDate(hoje, "dd/MM/yyyy");

        assertNotNull(dataFormatada);
    }

    @Test
    public void deveTransformarTextoEmData() {
        String dataString = "2020-07-30";

        LocalDate data = DateHelper.toLocalDate(dataString);

        assertNotNull(data);
    }
}
