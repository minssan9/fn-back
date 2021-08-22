package com.core.common.util;

import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

import static com.core.common.properties.StaticProperties.*;

@Component
public class DateUtil {
    public static LocalDateTime toDateFromString(String dateString){
        dateString = dateString.substring(0,4) + "-" + dateString.substring(4,6)+ "-" + dateString.substring(6,8);

        return LocalDateTime.of(
            LocalDate.parse(dateString, DATE_FORMAT),
            LocalDateTime.now().withHour(17).withMinute(0).withSecond(0).withNano(0).toLocalTime()) ;
    }

    public static String  toStringFromDate( LocalDateTime date){
        return date.format(DATE_STRING_FORMAT);
    }

    public static LocalDate getPreviousWorkingDay(LocalDate date) {
        DayOfWeek dayOfWeek = DayOfWeek.of(date.get(ChronoField.DAY_OF_WEEK));
        switch (dayOfWeek) {
            case MONDAY:
                return date.minus(3, ChronoUnit.DAYS);
            case SUNDAY:
                return date.minus(2, ChronoUnit.DAYS);
            default:
                return date.minus(1, ChronoUnit.DAYS);
        }
    }

    public static LocalDateTime getNowbyString() {
    	return LocalDateTime.parse(LocalDateTime.now().format(DATE_FORMAT2), DATE_FORMAT2);
    }

}
