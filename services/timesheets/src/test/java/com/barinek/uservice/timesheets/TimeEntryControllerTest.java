package com.barinek.uservice.timesheets;

import com.barinek.uservices.schema.TestDataSource;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@WebAppConfiguration
public class TimeEntryControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext applicationContext;

    @Autowired
    private DataSource dataSource;

    @Before
    public void setUp() throws Exception {
        TestDataSource.cleanWithFixtures(this.dataSource);

        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
    }

    @Test
    public void testCreate() throws Exception {
        String json = "{\"projectId\":2,\"userId\":1,\"date\":\"2015-05-17\",\"hours\":8}"; // account from fixtures

        mockMvc.perform(
                post("/time-entries")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {

                    TimeEntry actual = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), TimeEntry.class);
                    assertEquals(actual.getProjectId(), 2);
                    assertEquals(actual.getUserId(), 1);
                    assertEquals(actual.getHours(), 8);
                });
    }

    @Test
    public void testList() throws Exception {
        mockMvc.perform(
                get("/time-entries")
                        .param("userId", "2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    List<TimeEntry> entries = new ObjectMapper().readValue(
                            mvcResult.getResponse().getContentAsString(),
                            new TypeReference<List<TimeEntry>>() {
                            });
                    assertEquals(2, entries.size());
                    assertEquals(1, entries.get(0).getProjectId());
                    assertEquals(1, entries.get(1).getProjectId());
                });
    }
}