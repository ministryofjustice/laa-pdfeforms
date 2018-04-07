package com.moj.digital.laa.common.util;

import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;

import static org.assertj.core.api.Java6Assertions.assertThat;


public class LocalDateSQLDateConverterTest {

    private LocalDateSQLDateConverter localDateSQLDateConverter;
    
    @Before
    public  void setup(){
        localDateSQLDateConverter = new LocalDateSQLDateConverter();
        
    }
    @Test
    public void convertToDatabaseColumnShouldProduceSQLDate(){
        LocalDate localDate = LocalDate.now();
        Date sqlDate = localDateSQLDateConverter.convertToDatabaseColumn(localDate);
        assertThat(localDate).isEqualTo(sqlDate.toLocalDate());
    }

    @Test
    public void convertToEntityAttributeShouldProduceLocalDate(){
        Date sqlDate = Date.valueOf(LocalDate.now());
        LocalDate localDate = localDateSQLDateConverter.convertToEntityAttribute(sqlDate);
        assertThat(Date.valueOf(localDate)).isEqualTo(sqlDate);
    }

}