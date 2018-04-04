package com.moj.digital.laa.common.config;

import com.moj.digital.laa.exception.common.util.FieldsErrorExtractor;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

@Configuration
public class BindingResultProcessorConfiguration {

    @Bean
    public FieldsErrorExtractor bindingResultProcessor(){
        return new FieldsErrorExtractor();
    }
}
