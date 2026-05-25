package org.project.expressart;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ExpressartApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpressartApplication.class, args);
    }

}
