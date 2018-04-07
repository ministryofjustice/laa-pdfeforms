package com.moj.digital.laa.common.config;

import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import static org.assertj.core.api.Java6Assertions.assertThat;


public class MapperConfigurationTest {

    @Test
    public void modelMapperShouldGiveAValidModelMapperInstance() {
        ModelMapper modelMapper = new MapperConfiguration().modelMapper();
        assertThat(modelMapper).isNotNull();
        assertThat(modelMapper.getConfiguration().getMatchingStrategy()).isEqualTo(MatchingStrategies.LOOSE);
    }

}