package com.orakuma.servitus.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Date {

    private Date(){}

    public static String toString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }

    public static String toString(LocalDateTime dateTime) {
        return dateTime.toString();
    }
}
