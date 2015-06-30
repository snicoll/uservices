package com.barinek.uservice.backlog;

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
public class StoryRepository {
    private final JdbcTemplate template;

    @Autowired
    public StoryRepository(JdbcTemplate template) {
        this.template = template;
    }

    public Story create(Story story) throws SQLException {
        KeyHolder holder = new GeneratedKeyHolder();
        template.update(con -> {
            PreparedStatement ps = con.prepareStatement("insert into stories (project_id, name) values (?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, story.getProjectId());
            ps.setString(2, story.getName());
            return ps;
        }, holder);
        return new Story(holder.getKey().intValue(), story.getProjectId(), story.getName());
    }

    public List<Story> list(int id) throws SQLException {
        return template.query("select id, project_id, name from stories where project_id = ?", (rs, i) -> {
            return new Story(rs.getInt(1), rs.getInt(2), rs.getString(3));
        }, id);
    }
}