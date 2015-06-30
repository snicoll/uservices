package com.barinek.uservice.registration;

import com.barinek.uservices.accounts.AccountController;
import com.barinek.uservices.accounts.RegistrationController;
import com.barinek.uservices.projects.ProjectController;
import com.barinek.uservices.schema.MigrationsMySQL;
import com.barinek.uservices.users.UserController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.sql.DataSource;

@SpringBootApplication
@ComponentScan(basePackageClasses = {
        UserController.class,
        AccountController.class,
        RegistrationController.class,
        ProjectController.class
})
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