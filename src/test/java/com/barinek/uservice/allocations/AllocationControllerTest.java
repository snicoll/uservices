package com.barinek.uservice.allocations;

import com.barinek.uservice.AppRunner;
import com.barinek.uservice.TestDataSource;
import com.barinek.uservice.allocations.Allocation;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class AllocationControllerTest extends AppRunner {
    @Test
    public void testCreate() throws Exception {
        TestDataSource.cleanWithFixtures();

        String json = "{\"projectId\":2,\"userId\":1,\"firstDay\":\"2015-05-17\",\"lastDay\":\"2015-05-26\"}"; // project and user from fixtures
        String response = doPost("http://localhost:8080/allocations", json);

        Allocation actual = new ObjectMapper().readValue(response, Allocation.class);
        assertEquals(2, actual.getProjectId());
        assertEquals(1, actual.getUserId());
    }

    @Test
    public void testList() throws Exception {
        TestDataSource.cleanWithFixtures();

        String response = doGet("http://localhost:8080/allocations", new BasicNameValuePair("projectId", "1")); // allocations from fixtures

        List<Allocation> allocations = new ObjectMapper().readValue(response, new TypeReference<List<Allocation>>() {
        });
        Allocation allocation = allocations.get(0);
        assertEquals(1, allocation.getProjectId());
        assertEquals(1, allocation.getUserId());
    }
}