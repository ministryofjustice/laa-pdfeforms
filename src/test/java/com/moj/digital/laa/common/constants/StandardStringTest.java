package com.moj.digital.laa.common.constants;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;


public class StandardStringTest {

    @Test
    public void testStr(){
        assertThat(StandardString.COLON_WITH_SPACE.str()).isEqualTo(" : ");
    }
}