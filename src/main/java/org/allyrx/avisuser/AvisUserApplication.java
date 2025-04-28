package org.allyrx.avisuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class AvisUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(AvisUserApplication.class, args);
    }

}
