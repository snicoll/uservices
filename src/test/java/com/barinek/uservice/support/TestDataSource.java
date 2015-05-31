package com.barinek.uservice.support;

import com.barinek.uservice.utils.MigrationsMySQL;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class TestDataSource {
    private static HikariDataSource ds = new HikariDataSource();

    static {
        ds.setJdbcUrl("jdbc:mysql://localhost:3306/uservices_test?user=uservices&password=uservices");
        ds.setMinimumIdle(10);
        ds.setMaximumPoolSize(40);
    }

    public static DataSource getDataSource() {
        return ds;
    }

    public static void cleanWithFixtures() throws Exception {
        new MigrationsMySQL(getDataSource()).tryReset();
        new MigrationsMySQL(getDataSource()).tryMigrations();
        new TestFixtures(getDataSource()).tryFixtures();
    }

    public static void main(String[] args) throws Exception {
        TestDataSource.cleanWithFixtures();
    }
}