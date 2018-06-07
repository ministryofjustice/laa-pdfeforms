package com.moj.digital.laa.common.util;

import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.assertj.core.api.Java6Assertions.assertThat;


public class StringDateSQLDateConverterTest {

    private StringDateSQLDateConverter stringDateSQLDateConverter;
    
    @Before
    public  void setup(){
        stringDateSQLDateConverter = new StringDateSQLDateConverter();
        
    }
    @Test
    public void convertToDatabaseColumnShouldProduceSQLDate(){
        String stringDate = LocalDate.now().toString();
        Date sqlDate = stringDateSQLDateConverter.convertToDatabaseColumn(stringDate);
        assertThat(stringDate).isEqualTo(sqlDate.toString());
    }

    @Test
    public void convertToEntityAttributeShouldProduceLocalDate(){
        Date sqlDate = Date.valueOf(LocalDate.now());
        String stringDate = stringDateSQLDateConverter.convertToEntityAttribute(sqlDate);
        assertThat(Date.valueOf(stringDate)).isEqualTo(sqlDate);
    }

}