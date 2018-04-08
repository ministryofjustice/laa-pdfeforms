package com.moj.digital.laa.exception.common.util;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.moj.digital.laa.common.constants.StandardString.COLON_WITH_SPACE;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;


public class FieldsErrorExtractorTest {

    private FieldsErrorExtractor fieldsErrorExtractor;

    @Before
    public void setup() {
        fieldsErrorExtractor = new FieldsErrorExtractor();
    }

    @Test
    public void extractShouldReturnAllErrorMessages() {
        BindingResult bindingResult = mock(BindingResult.class);

        List<FieldError> listOfErrors = new ArrayList<>();
        listOfErrors.add(new FieldError("", "field1", "message1"));
        listOfErrors.add(new FieldError("", "field2", "message2"));
        listOfErrors.add(new FieldError("", "field3", "message3"));

        given(bindingResult.getFieldErrors()).willReturn(listOfErrors);

        String[] extractedMessages = fieldsErrorExtractor.extract(bindingResult);

        List<String> listOfFields = Arrays.stream(extractedMessages).map(defaultMessage -> defaultMessage.split(COLON_WITH_SPACE.str())[0]).collect(Collectors.toList());

        assertThat(listOfFields.size()).isEqualTo(3);
        assertThat(listOfFields).contains("field1");
        assertThat(listOfFields).contains("field2");
        assertThat(listOfFields).contains("field3");

    }

}