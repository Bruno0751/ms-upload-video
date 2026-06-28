package com.dev.ms_upload_video.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }

    @Bean
    @Qualifier("emailWebClient")
    public WebClient emailWebClient(
            WebClient.Builder builder,
            @Value("${email.service.url}") String baseUrl) {

        return builder
                .baseUrl(baseUrl)
                .build();
    }

}
