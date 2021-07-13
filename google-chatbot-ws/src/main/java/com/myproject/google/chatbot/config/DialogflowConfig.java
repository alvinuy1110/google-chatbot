package com.myproject.google.chatbot.config;

import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Default Jackson deserializer cannot deserialize webhook response from Google Dialoglow API.
 * For consuming Dialogflow webhook response, we need to use instances of JacksonFactory or GsonFactory provided by google-api-services-dialogflow.
 */
@Configuration
public class DialogflowConfig {

    // Deprecated due to security vulnerability
//    @Bean
//    public JacksonFactory jacksonFactory() {
//        return JacksonFactory.getDefaultInstance();
//    }

    @Bean
    public GsonFactory gsonFactory() {
        return GsonFactory.getDefaultInstance();
    }
}
