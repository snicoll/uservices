package com.barinek.uservice.dal;

import com.barinek.uservice.models.User;
import com.barinek.uservice.utils.BasicMapper;

import javax.sql.DataSource;
import java.sql.SQLException;

public class UserDAO {
    private final BasicMapper mapper;

    public UserDAO(DataSource dataSource) {
        this.mapper = new BasicMapper(dataSource);
    }

    public User create(User user) throws SQLException {
        return mapper.create("insert into users (name) values (?)", id -> {
            return new User(id, user.getName());
        }, user.getName());
    }

    public User show(int id) throws SQLException {
        return mapper.find("select id, name from users where id = ? limit 1", rs -> {
            return new User(rs.getInt(1), rs.getString(2));
        }, id).get(0);
    }
}