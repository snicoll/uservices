package com.barinek.uservice.projects;

import com.barinek.uservice.TestDataSource;
import com.barinek.uservice.projects.Project;
import com.barinek.uservice.projects.ProjectDAO;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProjectDAOTest {
    @Test
    public void testList() throws Exception {
        TestDataSource.cleanWithFixtures();
        DataSource dataSource = TestDataSource.getDataSource();

        int accountId = 142;
        int anotherAccountId = 143;

        ProjectDAO projectDAO = new ProjectDAO(dataSource);
        projectDAO.create(new Project(accountId, "aProject"));
        projectDAO.create(new Project(accountId, "anotherProject"));
        projectDAO.create(new Project(anotherAccountId, "andAnotherProject"));

        List<Project> projects = projectDAO.list(accountId);
        assertEquals(2, projects.size());
        assertEquals("aProject", projects.get(0).getName());
        assertEquals("anotherProject", projects.get(1).getName());
    }
}