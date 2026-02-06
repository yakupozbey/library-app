package com.education.libraryapp.integration.googlebooks.impl;

import feign.Client;
import feign.Feign;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DefaultFeignConfig {
    @Bean
    @Primary
    public Client feignClient() {
        return new Client.Default(null, null);
    }

    @Bean
    public Feign.Builder feignBuilder(Client client) {
        return Feign.builder()
                .client(client);
    }

}
