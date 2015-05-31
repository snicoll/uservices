package com.barinek.uservice.dal;

import com.barinek.uservice.models.Story;
import com.barinek.uservice.utils.BasicMapper;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class StoryDAO {
    private final BasicMapper mapper;

    public StoryDAO(DataSource dataSource) {
        this.mapper = new BasicMapper(dataSource);
    }

    public Story create(Story story) throws SQLException {
        return mapper.create("insert into stories (project_id, name) values (?, ?)", id -> {
            return new Story(id, story.getProjectId(), story.getName());
        }, story.getProjectId(), story.getName());
    }

    public List<Story> list(int id) throws SQLException {
        return mapper.find("select id, project_id, name from stories where project_id = ?", rs -> {
            return new Story(rs.getInt(1), rs.getInt(2), rs.getString(3));
        }, id);
    }
}