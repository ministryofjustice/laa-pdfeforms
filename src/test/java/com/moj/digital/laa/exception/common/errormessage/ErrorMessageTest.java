package com.moj.digital.laa.exception.common.errormessage;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class ErrorMessageTest {

    @Test
    public void testNumberOfElements() {
        assertThat(ErrorMessage.values().length).isEqualTo(2);
    }

    @Test
    public void testEnumElements() {
        assertThat(ErrorMessage.CLIENT_NOT_FOUND.message()).isEqualTo("No client found in the system with the given identifier %s");
        assertThat(ErrorMessage.CLIENT_PERSIST_ERROR.message()).isEqualTo("Error whilst persisting client information");
    }

}