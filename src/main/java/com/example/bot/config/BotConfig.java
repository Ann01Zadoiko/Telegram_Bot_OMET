package com.example.bot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@ComponentScan("com.example.bot")
@PropertySource("application.properties")
public class BotConfig {

    @Value("${bot.token}")
    String token;

    @Value("${bot.name}")
    String name;
}
