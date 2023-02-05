package com.example.phaseBotOmega.OpenAi;

import com.theokanning.openai.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class OpenAiConfig {

    @Value("${pb.openai.token}")
    private String openAItoken;

    @Bean
    public OpenAiService openAiService () {
        return new OpenAiService(openAItoken, Duration.ofMinutes(12));
    }
}
