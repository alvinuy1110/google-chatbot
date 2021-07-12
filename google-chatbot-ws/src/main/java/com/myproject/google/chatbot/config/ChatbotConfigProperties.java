package com.myproject.google.chatbot.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Configuration
@ConfigurationProperties(prefix = "chatbot.config")
@Data
@NoArgsConstructor
@Validated
@ToString
public class ChatbotConfigProperties {
    @NotEmpty
    private String name;
    @NotEmpty
    private String projectId;
    @NotEmpty
    private String languageCode;

}
