package ru.clevertec.ecl.util.hibernate.appConfig;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "ru.clevertec.ecl")
public class AppConfig {
}
