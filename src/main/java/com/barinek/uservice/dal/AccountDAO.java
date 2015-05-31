package com.barinek.uservice.dal;

import com.barinek.uservice.models.Account;
import com.barinek.uservice.utils.BasicMapper;

import javax.sql.DataSource;
import java.sql.SQLException;

public class AccountDAO {
    private final BasicMapper mapper;

    public AccountDAO(DataSource dataSource) {
        this.mapper = new BasicMapper(dataSource);
    }

    public Account create(Account account) throws SQLException {
        return mapper.create("insert into accounts (owner_id, name) values (?, ?)", id -> {
            return new Account(id, account.getOwnerId(), account.getName());
        }, account.getOwnerId(), account.getName());
    }

    public Account findFor(int id) throws SQLException {
        return mapper.find("select id, owner_id, name from accounts where owner_id = ? order by name desc limit 1", rs -> {
            return new Account(rs.getInt(1), rs.getInt(2), rs.getString(3));
        }, id).get(0);
    }
}