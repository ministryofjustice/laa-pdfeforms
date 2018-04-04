package com.moj.digital.laa.common.util;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;

public class DateUtil {

    public static Date sqlDateOf(int year, Month month, int day){
        LocalDate localDate = LocalDate.of(year, month, day);
        return Date.valueOf(localDate);
    }

}
