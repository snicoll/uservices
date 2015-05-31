package com.barinek.uservice;

import com.zaxxer.hikari.HikariDataSource;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    private final Server server;

    public static void main(String[] args) throws Exception {
        new App().start();
    }

    public App() throws IOException, ClassNotFoundException, SQLException {
        Properties properties = new Properties();
        properties.load(getClass().getResourceAsStream("/default.properties"));
        String defaultJson = properties.getProperty("container.json");

        HikariDataSource hikariDataSource = getHikariDataSource(defaultJson);

        MigrationsMySQL migrations = new MigrationsMySQL(hikariDataSource);
        migrations.tryMigrations();

        AccountDAO accountDAO = new AccountDAO(hikariDataSource);
        UserDAO userDAO = new UserDAO(hikariDataSource);

        HandlerList list = new HandlerList(); // ordered
        list.addHandler(new UserController(userDAO));
        list.addHandler(new AccountController(accountDAO));
        list.addHandler(new RegistrationController(new RegistrationService(userDAO, accountDAO)));
        list.addHandler(new ProjectController(new ProjectDAO(hikariDataSource)));
        list.addHandler(new AllocationController(new AllocationDAO(hikariDataSource)));
        list.addHandler(new StoryController(new StoryDAO(hikariDataSource)));
        list.addHandler(new TimeEntryController(new TimeEntryDAO(hikariDataSource)));
        list.addHandler(new DefaultController());

        server = new Server(tryPort(Integer.parseInt(properties.getProperty("server.port"))));
        server.setHandler(list);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    if (server.isRunning()) {
                        server.stop();
                    }
                    logger.info("App shutdown.");
                } catch (Exception e) {
                    logger.info("Error shutting down app.", e);
                }
            }
        });
    }

    public void start() throws Exception {
        logger.info("App started.");
        server.start();
    }

    public void stop() throws Exception {
        logger.info("App stopped.");
        server.stop();
    }

    /// PRIVATE HELPERS

    private int tryPort(int defaultPort) {
        return System.getenv("PORT") != null ? Integer.parseInt(System.getenv("PORT")) : defaultPort;
    }

    private HikariDataSource getHikariDataSource(String defaultJson) throws IOException {
        CredentialsMySQL credentials = CredentialsMySQL.from(System.getenv("VCAP_SERVICES") != null ? System.getenv("VCAP_SERVICES") : defaultJson);

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(credentials.getUrl());
        return dataSource;
    }
}