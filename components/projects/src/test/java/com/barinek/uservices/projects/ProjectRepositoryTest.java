package com.barinek.uservices.projects;

import com.barinek.uservices.schema.TestDataSource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration
public class ProjectRepositoryTest {

    @Autowired
    private DataSource dataSource;

    @SpringBootApplication
    public static class BasicApp {
    }

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    public void testList() throws Exception {
        TestDataSource.cleanWithFixtures(dataSource);

        int accountId = 142;
        int anotherAccountId = 143;

        projectRepository.create(new Project(accountId, "aProject"));
        projectRepository.create(new Project(accountId, "anotherProject"));
        projectRepository.create(new Project(anotherAccountId, "andAnotherProject"));

        List<Project> projects = projectRepository.list(accountId);
        Assert.assertEquals(2, projects.size());
        assertEquals("aProject", projects.get(0).getName());
        assertEquals("anotherProject", projects.get(1).getName());
    }
}