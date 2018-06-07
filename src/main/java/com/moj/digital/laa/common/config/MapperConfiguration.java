package com.moj.digital.laa.common.config;

import com.samskivert.mustache.Mustache;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ClassUtils;

@Configuration
public class MapperConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper;
    }

    @Bean
    public BeanPostProcessor mustacheCompilerTweak() {
        return new BeanPostProcessor() {
            private static final String BEAN_NAME = "mustacheCompiler";

            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                return bean;
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (ClassUtils.isAssignable(bean.getClass(), Mustache.Compiler.class) || BEAN_NAME.equals(beanName)) {
                    Mustache.Compiler compiler = (Mustache.Compiler) bean;
                    return compiler.defaultValue("").nullValue("");
                }

                return bean;
            }
        };
    }

}
