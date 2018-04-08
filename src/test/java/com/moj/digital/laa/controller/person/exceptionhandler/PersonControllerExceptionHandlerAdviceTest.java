package com.moj.digital.laa.controller.person.exceptionhandler;


import com.moj.digital.laa.exception.common.ErrorDetails;
import com.moj.digital.laa.exception.common.util.FieldsErrorExtractor;
import com.moj.digital.laa.exception.person.InvalidPersonDataException;
import com.moj.digital.laa.exception.person.PersonNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.moj.digital.laa.common.constants.StandardString.COLON_WITH_SPACE;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class PersonControllerExceptionHandlerAdviceTest {

    private PersonControllerExceptionHandlerAdvice personControllerExceptionHandlerAdvice;

    @Before
    public void setup() {
        FieldsErrorExtractor fieldsErrorExtractor = new FieldsErrorExtractor();
        personControllerExceptionHandlerAdvice = new PersonControllerExceptionHandlerAdvice(fieldsErrorExtractor);
    }

    @Test
    public void handleMethodArgumentNotValidShouldContainErrorDetaiAlongWithBadRequestasHttpStatusCode() {

        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        HttpHeaders headers = mock(HttpHeaders.class);
        WebRequest request = mock(WebRequest.class);

        BindingResult bindingResult = mock(BindingResult.class);

        List<FieldError> listOfErrors = new ArrayList<>();
        listOfErrors.add(new FieldError("", "field1", "message1"));
        listOfErrors.add(new FieldError("", "field2", "message2"));
        listOfErrors.add(new FieldError("", "field3", "message3"));

        given(bindingResult.getFieldErrors()).willReturn(listOfErrors);

        given(ex.getBindingResult()).willReturn(bindingResult);

        ResponseEntity<Object> response = personControllerExceptionHandlerAdvice.handleMethodArgumentNotValid(ex, headers, HttpStatus.OK, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getClass()).isEqualTo(ErrorDetails.class);

        ErrorDetails errorDetails = (ErrorDetails) response.getBody();

        List<String> listOfFields = Arrays.stream(errorDetails.getDetails()).map(defaultMessage -> defaultMessage.split(COLON_WITH_SPACE.str())[0]).collect(Collectors.toList());

        assertThat(listOfFields.size()).isEqualTo(3);
        assertThat(listOfFields).contains("field1");
        assertThat(listOfFields).contains("field2");
        assertThat(listOfFields).contains("field3");
    }

    @Test
    public void handleInvalidPersonDataExceptionShouldContainErrorDetails() {
        InvalidPersonDataException ivpe = new InvalidPersonDataException("CustomMessage", new Exception("ExceptionCauseMessage"));
        ErrorDetails errorDetails = personControllerExceptionHandlerAdvice.handleInvalidPersonDataException(ivpe);
        assertThat(errorDetails.getMessage()).isEqualTo("CustomMessage");
        assertThat(errorDetails.getDetails()[0]).isEqualTo("ExceptionCauseMessage");
    }

    @Test
    public void handlePersonNotFoundExceptionShouldContainErrorDetails() {
        PersonNotFoundException pnfe = new PersonNotFoundException("CustomMessage");
        ErrorDetails errorDetails = personControllerExceptionHandlerAdvice.handlePersonNotFoundException(pnfe);
        assertThat(errorDetails.getMessage()).isEqualTo("CustomMessage");

    }

}