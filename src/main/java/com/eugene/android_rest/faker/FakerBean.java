package com.eugene.android_rest.faker;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class FakerBean {

    @Bean
    public Faker getFaker() {
        return new Faker(new Locale("ru"));
    }
}
