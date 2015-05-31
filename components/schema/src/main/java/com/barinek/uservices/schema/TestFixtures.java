package com.barinek.uservices.schema;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class TestFixtures {
    private final DataSource dataSource;

    public TestFixtures(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void tryFixtures() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("insert into users (id, name) values (1, 'Jack'),(2, 'Fred')");
        list.add("insert into accounts (id, owner_id, name) values (1, 1, 'Jack''s account'),(2, 2, 'Fred''s account')");
        list.add("insert into projects (id, account_id, name) values (1, 1, 'Flagship'),(2, 1, 'Hovercraft')");
        list.add("insert into allocations (id, project_id, user_id, first_day, last_day) values (1, 1, 1, '2015-05-17', '2015-05-18'),(2, 1, 2, '2015-05-17', '2015-05-18')");
        list.add("insert into time_entries (id, project_id, user_id, date, hours) values (1, 1, 2, '2015-05-17', 5),(2, 1, 2, '2015-05-18', 3)");
        list.add("insert into stories (id, project_id, name) values (1, 1, 'aStory'),(2, 1, 'anotherStory')");

        try (Connection connection = dataSource.getConnection()) {
            for (String migration : list) {
                try (PreparedStatement statement = connection.prepareStatement(migration)) {
                    statement.executeUpdate();
                }
            }
        }
    }
}