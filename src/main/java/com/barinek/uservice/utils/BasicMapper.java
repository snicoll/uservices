package com.barinek.uservice.utils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BasicMapper {
    private final DataSource dataSource;

    public BasicMapper(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> T create(String sql, Id<Integer, T> id, Object... params) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                for (int i = 0; i < params.length; i++) {
                    Object param = params[i];
                    int parameterIndex = i + 1;
                    if (param.getClass().equals(String.class)) {
                        statement.setString(parameterIndex, (String) param);
                    }
                    if (param.getClass().equals(Integer.class)) {
                        statement.setInt(parameterIndex, (int) param);
                    }
                    if (param.getClass().equals(java.util.Date.class)) {
                        statement.setDate(parameterIndex, new Date(((java.util.Date) param).getTime()));
                    }
                }
                statement.executeUpdate();
                ResultSet keys = statement.getGeneratedKeys();
                keys.next();
                return id.apply(keys.getInt(1));
            }
        }
    }

    public <T> List<T> find(String sql, Mapper<ResultSet, T> mapper, int id) throws SQLException {
        List<T> results = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        results.add(mapper.apply(rs));
                    }
                }
            }
        }
        return results;
    }

    @FunctionalInterface
    public interface Mapper<ResultSet, T> {
        T apply(ResultSet rs) throws SQLException;
    }

    @FunctionalInterface
    public interface Id<Integer, T> {
        T apply(Integer id) throws SQLException;
    }
}