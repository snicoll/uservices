package com.barinek.uservice.timesheets;

import com.barinek.uservices.schema.MigrationsMySQL;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    CommandLineRunner migrate(DataSource dataSource) {
        return args -> {
            MigrationsMySQL migrations = new MigrationsMySQL(dataSource);
            migrations.tryMigrations();
        };
    }
}