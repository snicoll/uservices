package com.barinek.uservice.controllers;

import com.barinek.uservice.models.Project;
import com.barinek.uservice.support.AppRunner;
import com.barinek.uservice.support.TestDataSource;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProjectControllerTest extends AppRunner {
    @Test
    public void testCreate() throws Exception {
        TestDataSource.cleanWithFixtures();

        String json = "{\"accountId\":1,\"name\":\"aProject\"}"; // account from fixtures
        String response = doPost("http://localhost:8080/projects", json);

        Project actual = new ObjectMapper().readValue(response, Project.class);
        assertEquals(actual.getAccountId(), 1);
        assertEquals(actual.getName(), "aProject");
    }

    @Test
    public void testList() throws Exception {
        TestDataSource.cleanWithFixtures();

        String response = doGet("http://localhost:8080/projects", new BasicNameValuePair("accountId", "1")); // account and projects from fixtures

        List<Project> projects = new ObjectMapper().readValue(response, new TypeReference<List<Project>>() {
        });
        assertEquals(2, projects.size());
        assertEquals("Flagship", projects.get(0).getName());
        assertEquals("Hovercraft", projects.get(1).getName());
    }
}