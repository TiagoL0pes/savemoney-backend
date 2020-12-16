package com.savemoney.utils.helpers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

public class DateHelper {

    public static String formatDate(Temporal date, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(date);
    }

    public static LocalDate toLocalDate(String dateTime) {
        DateTimeFormatter formatter;
        boolean isFormatted = dateTime.matches("\\d{2}/\\d{2}/\\d{4}");
        formatter = isFormatted ?
                DateTimeFormatter.ofPattern("dd/MM/yyyy") :
                DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateTime, formatter);
    }
}
