package com.barinek.uservices.projects;


import com.barinek.uservices.restsupport.BasicApp;
import com.barinek.uservices.restsupport.RestTestSupport;
import com.barinek.uservices.schema.TestDataSource;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.eclipse.jetty.server.handler.HandlerList;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class ProjectControllerTest extends RestTestSupport {
    BasicApp app = new BasicApp() {
        @Override
        public HandlerList getHandlers(Properties properties) throws Exception {
            DataSource ds = TestDataSource.getDataSource();
            HandlerList list = new HandlerList();
            list.addHandler(new ProjectController(new ProjectDAO(ds)));
            return list;
        }
    };

    @Before
    public void setUp() throws Exception {
        app.start();
    }

    @After
    public void tearDown() throws Exception {
        app.stop();
    }

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
        Assert.assertEquals(2, projects.size());
        assertEquals("Flagship", projects.get(0).getName());
        assertEquals("Hovercraft", projects.get(1).getName());
    }
}