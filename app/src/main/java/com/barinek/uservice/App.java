package com.barinek.uservice;


import com.barinek.uservice.allocations.AllocationController;
import com.barinek.uservice.backlog.StoryController;
import com.barinek.uservice.timesheets.TimeEntryController;
import com.barinek.uservices.accounts.AccountController;
import com.barinek.uservices.accounts.RegistrationController;
import com.barinek.uservices.projects.ProjectController;
import com.barinek.uservices.users.UserController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = {
        UserController.class,
        AccountController.class,
        RegistrationController.class,
        ProjectController.class,
        AllocationController.class,
        StoryController.class,
        TimeEntryController.class
})
public class App {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(App.class, args);
    }
}