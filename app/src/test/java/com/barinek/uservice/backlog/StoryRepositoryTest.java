package com.barinek.uservice.backlog;

import com.barinek.uservice.App;
import com.barinek.uservices.schema.TestDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
public class StoryRepositoryTest {
    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private DataSource dataSource;

    @Test
    public void testList() throws Exception {
        TestDataSource.cleanWithFixtures(this.dataSource);

        int projectId = 156;
        int anotherProjectId = 157;

        storyRepository.create(new Story(projectId, "aStory"));
        storyRepository.create(new Story(anotherProjectId, "anotherStory"));

        List<Story> stories = storyRepository.list(anotherProjectId);
        assertEquals(1, stories.size());
        assertEquals(anotherProjectId, stories.get(0).getProjectId());
    }
}