package com.justai.vkquotebot;

import com.justai.vkquotebot.config.VkQuoteBotConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties(VkQuoteBotConfigProperties.class)
public class VkQuoteBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(VkQuoteBotApplication.class, args);
    }

}
