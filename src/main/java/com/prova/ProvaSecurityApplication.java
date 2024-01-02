package com.prova;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProvaSecurityApplication {

    @Bean
    public ModelMapper getInstance()
    {
        return new ModelMapper();
    }


    public static void main(String[] args) {
        SpringApplication.run(ProvaSecurityApplication.class, args);
    }

}
