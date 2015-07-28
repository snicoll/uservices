package com.barinek.uservices.schema;

import javax.sql.DataSource;

public class TestDataSource {
    public static void cleanWithFixtures(DataSource dataSource) throws Exception {
        new MigrationsDataSource(dataSource).tryReset();
        new MigrationsDataSource(dataSource).tryMigrations();
        new TestFixtures(dataSource).tryFixtures();
    }
}