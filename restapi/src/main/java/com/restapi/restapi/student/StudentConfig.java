package com.restapi.restapi.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student mariam = new Student(
                    "Mariam",
                    "Mariam.jamal@gmail.com",
                    LocalDate.of(1999, Month.MARCH, 15)
            );

            Student alex = new Student(
                    "Alex",
                    "Alex@gmail.com",
                    LocalDate.of(1997, Month.APRIL, 13)
            );

            repository.saveAll(
                    Arrays.asList(mariam, alex)
            );
        };
    }
}
