package com.barinek.uservices.restsupport;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class BasicApp {
    protected static final Logger logger = LoggerFactory.getLogger(BasicApp.class);
    protected final Server server;

    public BasicApp() throws RuntimeException {
        Properties properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/default.properties"));
            server = new Server(tryPort(Integer.parseInt(properties.getProperty("server.port"))));
            server.setHandler(getHandlers(properties));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

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

    public HandlerList getHandlers(Properties properties) throws Exception {
        return new HandlerList();
    }

    private int tryPort(int defaultPort) {
        return System.getenv("PORT") != null ? Integer.parseInt(System.getenv("PORT")) : defaultPort;
    }
}