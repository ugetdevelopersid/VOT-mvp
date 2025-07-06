package com.ugetsidfashion.vot;

import com.google.genai.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VotApplicationConfiguration {

    @Bean
    public Client geminiClient() {
        // Initialize the Gemini client with the necessary configuration
        return Client.builder()
                .apiKey(System.getenv("GEMINI_API_KEY"))
                .build();
    }

}
