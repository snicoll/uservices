package com.barinek.uservice;

import com.barinek.uservice.allocations.AllocationController;
import com.barinek.uservice.allocations.AllocationDAO;
import com.barinek.uservice.backlog.StoryController;
import com.barinek.uservice.backlog.StoryDAO;
import com.barinek.uservice.timesheets.TimeEntryController;
import com.barinek.uservice.timesheets.TimeEntryDAO;
import com.barinek.uservices.accounts.AccountController;
import com.barinek.uservices.accounts.AccountDAO;
import com.barinek.uservices.accounts.RegistrationController;
import com.barinek.uservices.accounts.RegistrationService;
import com.barinek.uservices.jdbcsupport.CredentialsMySQL;
import com.barinek.uservices.projects.ProjectController;
import com.barinek.uservices.projects.ProjectDAO;
import com.barinek.uservices.restsupport.BasicApp;
import com.barinek.uservices.restsupport.DefaultController;
import com.barinek.uservices.schema.MigrationsMySQL;
import com.barinek.uservices.users.UserController;
import com.barinek.uservices.users.UserDAO;
import com.zaxxer.hikari.HikariDataSource;
import org.eclipse.jetty.server.handler.HandlerList;

import java.io.IOException;
import java.util.Properties;

public class App extends BasicApp {

    public static void main(String[] args) throws Exception {
        App app = new App();
        app.start();
    }

    public HandlerList getHandlers(Properties properties) throws Exception {
        HandlerList list = new HandlerList(); // ordered

        String defaultJson = properties.getProperty("container.json");

        HikariDataSource hikariDataSource = getHikariDataSource(defaultJson);

        MigrationsMySQL migrations = new MigrationsMySQL(hikariDataSource);
        migrations.tryMigrations();

        AccountDAO accountDAO = new AccountDAO(hikariDataSource);
        UserDAO userDAO = new UserDAO(hikariDataSource);

        list.addHandler(new UserController(userDAO));
        list.addHandler(new AccountController(accountDAO));
        list.addHandler(new RegistrationController(new RegistrationService(userDAO, accountDAO)));
        list.addHandler(new ProjectController(new ProjectDAO(hikariDataSource)));
        list.addHandler(new AllocationController(new AllocationDAO(hikariDataSource)));
        list.addHandler(new StoryController(new StoryDAO(hikariDataSource)));
        list.addHandler(new TimeEntryController(new TimeEntryDAO(hikariDataSource)));
        list.addHandler(new DefaultController());
        return list;
    }

    private HikariDataSource getHikariDataSource(String defaultJson) throws IOException {
        CredentialsMySQL credentials = CredentialsMySQL.from(System.getenv("VCAP_SERVICES") != null ? System.getenv("VCAP_SERVICES") : defaultJson);
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(credentials.getUrl());
        return dataSource;
    }
}