package com.barinek.uservices.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class AccountRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AccountRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Account create(Account account) throws SQLException {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement("insert into accounts (owner_id, name) values (?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, account.getOwnerId());
            ps.setString(2, account.getName());
            return ps;
        }, holder);
        return new Account(holder.getKey().intValue(), account.getOwnerId(), account.getName());
    }

    public Account findFor(int id) throws SQLException {
        return jdbcTemplate.queryForObject("select id, owner_id, name from accounts where owner_id = ? order by name desc limit 1", (rs, i) -> {
            return new Account(rs.getInt(1), rs.getInt(2), rs.getString(3));
        }, id);
    }
}