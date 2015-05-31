package com.barinek.uservice.timesheets;

import com.barinek.uservices.restsupport.RestTestSupport;
import com.barinek.uservices.schema.TestDataSource;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TimeEntryControllerTest extends RestTestSupport {
    App app;

    @Before
    public void setUp() throws Exception {
        app = new App();
        app.start();
    }

    @After
    public void tearDown() throws Exception {
        app.stop();
    }

    @Test
    public void testCreate() throws Exception {
        TestDataSource.cleanWithFixtures();

        String json = "{\"projectId\":2,\"userId\":1,\"date\":\"2015-05-17\",\"hours\":8}"; // account from fixtures
        String response = doPost("http://localhost:8083/time-entries", json);

        TimeEntry actual = new ObjectMapper().readValue(response, TimeEntry.class);
        assertEquals(actual.getProjectId(), 2);
        assertEquals(actual.getUserId(), 1);
        assertEquals(actual.getHours(), 8);
    }

    @Test
    public void testList() throws Exception {
        TestDataSource.cleanWithFixtures();

        String response = doGet("http://localhost:8083/time-entries", new BasicNameValuePair("userId", "2")); // account and projects from fixtures

        List<TimeEntry> entries = new ObjectMapper().readValue(response, new TypeReference<List<TimeEntry>>() {
        });
        assertEquals(2, entries.size());
        assertEquals(1, entries.get(0).getProjectId());
        assertEquals(1, entries.get(1).getProjectId());
    }
}