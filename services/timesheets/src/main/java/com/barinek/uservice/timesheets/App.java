package com.barinek.uservice.timesheets;

import com.barinek.uservices.jdbcsupport.CredentialsMySQL;
import com.barinek.uservices.restsupport.BasicApp;
import com.barinek.uservices.restsupport.DefaultController;
import com.barinek.uservices.schema.MigrationsMySQL;
import com.zaxxer.hikari.HikariDataSource;
import org.eclipse.jetty.server.handler.HandlerList;

import java.io.IOException;
import java.util.Properties;

public class App extends BasicApp {
    Integer port = null;

    public static void main(String[] args) throws Exception {
        App app = new App();
        app.start();
    }

    @Override
    protected String defaultProperties() {
        return "/timesheets.properties";
    }

    public HandlerList getHandlers(Properties properties) throws Exception {
        HandlerList list = new HandlerList(); // ordered

        String defaultJson = properties.getProperty("container.json");

        HikariDataSource hikariDataSource = getHikariDataSource(defaultJson);

        MigrationsMySQL migrations = new MigrationsMySQL(hikariDataSource);
        migrations.tryMigrations();

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