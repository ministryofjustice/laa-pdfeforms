package com.moj.digital.laa.exception.common;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Java6Assertions.assertThat;


public class ErrorDetailsTest {

    @Test
    public void errorDetailsShouldBeAbleToHoldPassedInErrorDetails() {
        LocalDateTime localDate = LocalDateTime.now();
        String errorMessage = "errorMessage";

        ErrorDetails errorDetails = new ErrorDetails(localDate, errorMessage);

        assertThat(errorDetails.getDate()).isEqualTo(localDate);
        assertThat(errorDetails.getMessage()).isEqualTo(errorMessage);
        assertThat(errorDetails.getDetails()).isEmpty();

        errorDetails = new ErrorDetails(localDate, errorMessage, "field0ErrorMessage", "field1ErrorMessage", "field2ErrorMessage");

        assertThat(errorDetails.getDate()).isEqualTo(localDate);
        assertThat(errorDetails.getMessage()).isEqualTo(errorMessage);
        assertThat(errorDetails.getDetails().length).isEqualTo(3);
        assertThat(errorDetails.getDetails()[0]).isEqualTo("field0ErrorMessage");
        assertThat(errorDetails.getDetails()[1]).isEqualTo("field1ErrorMessage");
        assertThat(errorDetails.getDetails()[2]).isEqualTo("field2ErrorMessage");

    }

}