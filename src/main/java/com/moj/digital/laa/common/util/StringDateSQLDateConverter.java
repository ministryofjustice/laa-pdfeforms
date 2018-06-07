package com.moj.digital.laa.common.util;

import javax.persistence.AttributeConverter;
import java.sql.Date;
import java.time.LocalDate;

public class StringDateSQLDateConverter implements AttributeConverter<String, Date> {

    @Override
    public Date convertToDatabaseColumn(String locDate) {
        return (locDate == null ? null : Date.valueOf(locDate));
    }

    @Override
    public String convertToEntityAttribute(Date sqlDate) {
        return (sqlDate == null ? null : sqlDate.toString());
    }
}