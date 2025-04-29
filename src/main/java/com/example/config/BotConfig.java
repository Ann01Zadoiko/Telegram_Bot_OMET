package com.example.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@ComponentScan("com.example")
@PropertySource("application.properties")
public class BotConfig {

    @Value("${bot.token}")
    String token;

    @Value("${bot.username}")
    String name;
}
