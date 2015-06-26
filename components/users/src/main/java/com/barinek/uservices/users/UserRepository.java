package com.barinek.uservices.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class UserRepository {
    private final JdbcTemplate template;

    @Autowired
    public UserRepository(JdbcTemplate template) {
        this.template = template;
    }

    public User create(User user) throws SQLException {
        KeyHolder holder = new GeneratedKeyHolder();
        template.update(con -> {
            PreparedStatement ps = con.prepareStatement("insert into users (name) values (?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getName());
            return ps;
        }, holder);
        return new User(holder.getKey().intValue(), user.getName());
    }

    public User show(int id) throws SQLException {
        return template.queryForObject("select id, name from users where id = ? limit 1", (rs, rowNum) -> {
            return new User(rs.getInt(1), rs.getString(2));
        }, id);
    }
}