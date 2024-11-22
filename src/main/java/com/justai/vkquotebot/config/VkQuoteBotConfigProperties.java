package com.justai.vkquotebot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "vk")
public class VkQuoteBotConfigProperties {
    private String token;
    private String confirmationCode;
}
