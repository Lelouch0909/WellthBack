package com.lontsi.wellthappback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WellthAppBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(WellthAppBackApplication.class, args);
    }

}
