package com.barinek.uservice;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class TimeEntryDAO {
    private final BasicMapper mapper;

    public TimeEntryDAO(DataSource dataSource) {
        this.mapper = new BasicMapper(dataSource);
    }

    public TimeEntry create(TimeEntry entry) throws SQLException {
        return mapper.create("insert into time_entries (project_id, user_id, date, hours) values (?, ?, ?, ?)", id -> {
            return new TimeEntry(id, entry.getProjectId(), entry.getUserId(), entry.getDate(), entry.getHours());
        }, entry.getProjectId(), entry.getUserId(), entry.getDate(), entry.getHours());
    }

    public List<TimeEntry> list(int id) throws SQLException {
        return mapper.find("select id, project_id, user_id, date, hours from time_entries where user_id = ?", rs -> {
            return new TimeEntry(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getDate(4), rs.getInt(5));
        }, id);
    }
}