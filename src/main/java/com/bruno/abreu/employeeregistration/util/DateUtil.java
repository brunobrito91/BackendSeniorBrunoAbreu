package com.bruno.abreu.employeeregistration.util;

import java.time.LocalDate;
import java.time.Period;

public class DateUtil {

    public static Integer getAge(LocalDate birthDate) {
        Period period = Period.between(birthDate, LocalDate.now());
        return period.getYears();
    }

    public static LocalDate getDateFromYearsAgo(int years) {
        return LocalDate.now().minusYears(years);
    }
}
