package com.barinek.uservices.projects;

import com.barinek.uservices.jdbcsupport.BasicMapper;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class ProjectDAO {
    private final BasicMapper mapper;

    public ProjectDAO(DataSource dataSource) throws SQLException {
        this.mapper = new BasicMapper(dataSource);
    }

    public Project create(Project project) throws SQLException {
        return mapper.create("insert into projects (account_id, name) values (?, ?)", id -> {
            return new Project(id, project.getAccountId(), project.getName());
        }, project.getAccountId(), project.getName());
    }

    public List<Project> list(int id) throws SQLException {
        return mapper.find("select id, account_id, name from projects where account_id = ?", rs -> {
            return new Project(rs.getInt(1), rs.getInt(2), rs.getString(3));
        }, id);
    }
}