package com.myproject.google.chatbot.config;

import com.myproject.google.chatbot.service.DialogFlowService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
public class ChatbotConfig {

    @Bean
    public DialogFlowService dialogFlowService() {
        return new DialogFlowService();
    }
}
