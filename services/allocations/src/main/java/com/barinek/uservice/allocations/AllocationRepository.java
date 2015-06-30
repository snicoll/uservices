package com.barinek.uservice.allocations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class AllocationRepository {
    private final JdbcTemplate template;

    @Autowired
    public AllocationRepository(JdbcTemplate template) {
        this.template = template;
    }

    public Allocation create(Allocation allocation) throws SQLException {
        KeyHolder holder = new GeneratedKeyHolder();
        template.update(con -> {
            PreparedStatement ps = con.prepareStatement("insert into allocations (project_id, user_id, first_day, last_day) values (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, allocation.getProjectId());
            ps.setInt(2, allocation.getUserId());
            ps.setDate(3, new java.sql.Date(allocation.getFirstDay().getTime()));
            ps.setDate(4, new java.sql.Date(allocation.getLastDay().getTime()));
            return ps;
        }, holder);
        return new Allocation(holder.getKey().intValue(), allocation.getProjectId(), allocation.getUserId(), allocation.getFirstDay(), allocation.getLastDay());
    }

    public List<Allocation> list(int id) throws SQLException {
        return template.query("select id, project_id, user_id, first_day, last_day from allocations where project_id = ?", (rs, rowNum) -> {
            return new Allocation(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getDate(4), rs.getDate(5));
        }, id);
    }
}
