package com.justai.vkbotapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "vk")
@Data
public class VkConfig {
    private String confirmationToken;
    private String accessToken;
    private String apiVersion;
    private String groupId;
    private String apiUrl;
}