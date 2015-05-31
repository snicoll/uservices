package com.barinek.uservice.utils;

import com.barinek.uservice.models.Story;
import com.barinek.uservice.support.TestDataSource;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class BasicMapperTest {
    @Test
    public void testFind() throws Exception {
        BasicMapper template = new BasicMapper(TestDataSource.getDataSource());

        int id = 42;
        String sql = "select id, name from (select 42 as id, 'apples' as name) as dates where id = ?";

        List<String> names = template.find(sql, rs -> {
            return rs.getString(2);
        }, id);
        assertEquals("apples", names.get(0));
    }

    @Test
    public void testCreate() throws Exception {
        BasicMapper template = new BasicMapper(TestDataSource.getDataSource());

        int projectId = 156;
        String name = "aStory";

        String sql = "insert into stories (project_id, name) values (?, ?)"; // sorry, assumes table exists
        Story story = template.create(sql, id -> {
            return new Story(id, projectId, name);
        }, projectId, name);
        assertEquals("aStory", story.getName());
    }
}