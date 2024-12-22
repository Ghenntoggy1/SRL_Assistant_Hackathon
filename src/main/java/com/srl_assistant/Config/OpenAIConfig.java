package com.srl_assistant.Config;

import com.theokanning.openai.service.OpenAiService;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class OpenAIConfig {
    @Value("${OPENAI_API_KEY}")
    private String apiKey;


    @Bean
    public OpenAiService openAiService() {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalArgumentException("OpenAI API key is missing. Set the 'OPENAI_API_KEY' environment variable.");
        }
        return new OpenAiService(apiKey, Duration.ofSeconds(60));
    }
}

