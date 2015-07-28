package com.barinek.uservices.schema;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MigrationsDataSource {
    private final DataSource dataSource;

    public MigrationsDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void tryReset() throws SQLException {
        List<String> list = new ArrayList<>();
        list.add("drop table if exists stories");
        list.add("drop table if exists time_entries");
        list.add("drop table if exists allocations");
        list.add("drop table if exists projects");
        list.add("drop table if exists accounts");
        list.add("drop table if exists users");
        run(list);
    }

    public void tryMigrations() throws SQLException {
        List<String> list = new ArrayList<>();
        list.add("create table if not exists users (id int not null auto_increment primary key, name varchar(256));");
        list.add("create table if not exists accounts (id int not null auto_increment primary key, owner_id int, name varchar(256));");
        list.add("create table if not exists projects (id int not null auto_increment primary key, account_id int, name varchar(256));");
        list.add("create table if not exists allocations (id int not null auto_increment primary key, project_id int, user_id int, first_day datetime, last_day datetime);");
        list.add("create table if not exists time_entries (id int not null auto_increment primary key, project_id int, user_id int, date datetime, hours int);");
        list.add("create table if not exists stories (id int not null auto_increment primary key, project_id int, name varchar(256));");
        run(list);
    }

    private void run(List<String> migrations) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            for (String migration : migrations) {
                try (PreparedStatement statement = connection.prepareStatement(migration)) {
                    statement.executeUpdate();
                }
            }
        }
    }
}