package com.barinek.uservice;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MigrationsMySQL {
    private final DataSource dataSource;

    public MigrationsMySQL(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void tryReset() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("drop table if exists stories")) {
                statement.executeUpdate();
            }
            try (PreparedStatement statement = connection.prepareStatement("drop table if exists time_entries")) {
                statement.executeUpdate();
            }
            try (PreparedStatement statement = connection.prepareStatement("drop table if exists allocations")) {
                statement.executeUpdate();
            }
            try (PreparedStatement statement = connection.prepareStatement("drop table if exists projects")) {
                statement.executeUpdate();
            }
            try (PreparedStatement statement = connection.prepareStatement("drop table if exists accounts")) {
                statement.executeUpdate();
            }
            try (PreparedStatement statement = connection.prepareStatement("drop table if exists users")) {
                statement.executeUpdate();
            }
        }
    }

    public void tryMigrations() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "create table if not exists users (id int not null auto_increment primary key, name varchar(256));")) {
                statement.executeUpdate();
            }
            try (PreparedStatement statement = connection.prepareStatement(
                    "create table if not exists accounts (id int not null auto_increment primary key, owner_id int, name varchar(256));")) {
                statement.executeUpdate();
            }
            try (PreparedStatement statement = connection.prepareStatement(
                    "create table if not exists projects (id int not null auto_increment primary key, account_id int, name varchar(256));")) {
                statement.executeUpdate();
            }
            try (PreparedStatement statement = connection.prepareStatement(
                    "create table if not exists allocations (id int not null auto_increment primary key, project_id int, user_id int, first_day datetime, last_day datetime);")) {
                statement.executeUpdate();
            }
            try (PreparedStatement statement = connection.prepareStatement(
                    "create table if not exists time_entries (id int not null auto_increment primary key, project_id int, user_id int, date datetime, hours int);")) {
                statement.executeUpdate();
            }
            try (PreparedStatement statement = connection.prepareStatement(
                    "create table if not exists stories (id int not null auto_increment primary key, project_id int, name varchar(256));")) {
                statement.executeUpdate();
            }
        }
    }
}