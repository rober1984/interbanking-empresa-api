package com.interbanking.empresa.api.domain.vo;

import java.time.LocalDate;
import java.time.LocalTime;

public record RangoMes(LocalDate inicio, LocalDate fin) {

    public static RangoMes mesPasado() {
        LocalDate mesPasado = LocalDate.now().minusMonths(1);
        LocalDate inicio = LocalDate.from(mesPasado.withDayOfMonth(1).atStartOfDay());
        LocalDate fin = LocalDate.from(mesPasado.withDayOfMonth(mesPasado.lengthOfMonth()).atTime(LocalTime.MAX));
        return new RangoMes(inicio, fin);
    }
}
