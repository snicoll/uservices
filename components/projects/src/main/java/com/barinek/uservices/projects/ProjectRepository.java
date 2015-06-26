package com.barinek.uservices.projects;

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
public class ProjectRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Project create(Project project) throws SQLException {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement("insert into projects (account_id, name) values (?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, project.getAccountId());
            ps.setString(2, project.getName());
            return ps;
        }, holder);

        return new Project(holder.getKey().intValue(), project.getAccountId(), project.getName());
    }

    public List<Project> list(int id) throws SQLException {
        return jdbcTemplate.query("select id, account_id, name from projects where account_id = ?", (rs, i) -> {
            return new Project(rs.getInt(1), rs.getInt(2), rs.getString(3));
        }, id);
    }
}