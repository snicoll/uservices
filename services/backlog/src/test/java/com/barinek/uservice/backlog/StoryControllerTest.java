package com.barinek.uservice.backlog;

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
public class StoryControllerTest {
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
        String json = "{\"projectId\":2,\"name\":\"An epic story\"}"; // account from fixtures

        mockMvc.perform(
                post("/stories")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    String response = mvcResult.getResponse().getContentAsString();
                    Story actual = new ObjectMapper().readValue(response, Story.class);
                    assertEquals(actual.getProjectId(), 2);
                    assertEquals(actual.getName(), "An epic story");
                });
    }

    @Test
    public void testList() throws Exception {
        mockMvc.perform(
                get("/stories")
                        .param("projectId", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    List<Story> stories = new ObjectMapper().readValue(
                            mvcResult.getResponse().getContentAsString(),
                            new TypeReference<List<Story>>() {
                            });
                    assertEquals(2, stories.size());
                    assertEquals(1, stories.get(0).getProjectId());
                    assertEquals(1, stories.get(1).getProjectId());
                });
    }
}