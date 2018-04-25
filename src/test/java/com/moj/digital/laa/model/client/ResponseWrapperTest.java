package com.moj.digital.laa.model.client;

import com.moj.digital.laa.model.common.ResponseWrapper;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class ResponseWrapperTest {

    @Test
    public void uponInstantiationShouldReturnResponseMessage(){
        ResponseWrapper responseWrapper = new ResponseWrapper("responseMessage");
        assertThat(responseWrapper.getResponseMessage()).isEqualTo("responseMessage");
    }
}