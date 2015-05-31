package com.barinek.uservice;

import org.apache.commons.io.IOUtils;

import javax.sql.DataSource;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class TestFixtures {
    private final DataSource dataSource;

    public TestFixtures(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void tryFixtures() throws Exception {
        URL url = TestDataSource.class.getResource("/fixtures");
        try (Connection connection = dataSource.getConnection()) {
            for (String fixture : new File(url.toURI()).list()) {
                String sql = IOUtils.toString(getClass().getResourceAsStream(String.format("/fixtures/%s", fixture)));
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.execute();
                }
            }
        }
    }
}