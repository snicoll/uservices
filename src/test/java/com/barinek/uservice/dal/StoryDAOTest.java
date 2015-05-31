package com.barinek.uservice.dal;

import com.barinek.uservice.models.Story;
import com.barinek.uservice.support.TestDataSource;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class StoryDAOTest {
    @Test
    public void testList() throws Exception {
        TestDataSource.cleanWithFixtures();
        DataSource dataSource = TestDataSource.getDataSource();

        int projectId = 156;
        int anotherProjectId = 157;

        StoryDAO storyDAO = new StoryDAO(dataSource);
        storyDAO.create(new Story(projectId, "aStory"));
        storyDAO.create(new Story(anotherProjectId, "anotherStory"));

        List<Story> stories = storyDAO.list(anotherProjectId);
        assertEquals(1, stories.size());
        assertEquals(anotherProjectId, stories.get(0).getProjectId());
    }
}