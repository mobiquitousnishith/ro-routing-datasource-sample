package com.nishith.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nishith.mapper.ProgrammingLanguageMapper;

@Configuration
public class MapperConfig {

    @Bean
    public ProgrammingLanguageMapper programmingLanguageMapper() {
        return ProgrammingLanguageMapper.INSTANCE;
    }
}
