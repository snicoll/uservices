package com.barinek.uservice.timesheets;

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
public class TimeEntryRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TimeEntryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public TimeEntry create(TimeEntry timeEntry) throws SQLException {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement("insert into time_entries (project_id, user_id, date, hours) values (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, timeEntry.getProjectId());
            ps.setInt(2, timeEntry.getUserId());
            ps.setDate(3, new java.sql.Date(timeEntry.getDate().getTime()));
            ps.setInt(4, timeEntry.getHours());
            return ps;
        }, holder);
        return new TimeEntry(holder.getKey().intValue(), timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours());
    }


    public List<TimeEntry> list(int id) throws SQLException {
        return jdbcTemplate.query("select id, project_id, user_id, date, hours from time_entries where user_id = ?", (rs, i) -> {
            return new TimeEntry(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getDate(4), rs.getInt(5));
        }, id);
    }
}