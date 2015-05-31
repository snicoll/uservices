package com.barinek.uservice.dal;

import com.barinek.uservice.models.Allocation;
import com.barinek.uservice.utils.BasicMapper;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class AllocationDAO {
    private final BasicMapper mapper;

    public AllocationDAO(DataSource dataSource) {
        this.mapper = new BasicMapper(dataSource);
    }

    public Allocation create(Allocation allocation) throws SQLException {
        return mapper.create("insert into allocations (project_id, user_id, first_day, last_day) values (?, ?, ?, ?)", id -> {
            return new Allocation(id, allocation.getProjectId(), allocation.getUserId(), allocation.getFirstDay(), allocation.getLastDay());
        }, allocation.getProjectId(), allocation.getUserId(), allocation.getFirstDay(), allocation.getLastDay());
    }

    public List<Allocation> list(int id) throws SQLException {
        return mapper.find("select id, project_id, user_id, first_day, last_day from allocations where project_id = ?", rs -> {
            return new Allocation(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getDate(4), rs.getDate(5));
        }, id);
    }
}
