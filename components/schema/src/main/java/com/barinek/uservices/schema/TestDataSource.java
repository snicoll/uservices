package com.barinek.uservices.schema;

import javax.sql.DataSource;

public class TestDataSource {
    public static void cleanWithFixtures(DataSource dataSource) throws Exception {
        new MigrationsMySQL(dataSource).tryReset();
        new MigrationsMySQL(dataSource).tryMigrations();
        new TestFixtures(dataSource).tryFixtures();
    }
}