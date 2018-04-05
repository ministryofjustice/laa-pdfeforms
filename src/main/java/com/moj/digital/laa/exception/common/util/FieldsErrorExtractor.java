package com.moj.digital.laa.exception.common.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

import static com.moj.digital.laa.common.constants.StandardString.COLON_WITH_SPACE;

public class FieldsErrorExtractor {

    public String[] extract(BindingResult bindingResult) {
        List<String> fieldErrors = new ArrayList<>();

        bindingResult.getFieldErrors().forEach(error -> {
            fieldErrors.add(buildErrorString(error));
        });

        return fieldErrors.toArray(new String[0]);
    }

    private String buildErrorString(FieldError error) {
        return new StringBuilder()
                .append(error.getField())
                .append(COLON_WITH_SPACE.str())
                .append(error.getDefaultMessage())
                .toString();
    }

}
