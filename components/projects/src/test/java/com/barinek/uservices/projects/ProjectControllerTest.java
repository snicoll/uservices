package com.barinek.uservices.projects;


import com.barinek.uservices.schema.TestDataSource;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
@SpringApplicationConfiguration
@WebAppConfiguration
public class ProjectControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext applicationContext;

    @Autowired
    private DataSource dataSource;

    @SpringBootApplication
    public static class BasicApp {
    }

    @Before
    public void setUp() throws Exception {
        TestDataSource.cleanWithFixtures(dataSource);
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
    }

    @Test
    public void testCreate() throws Exception {
        String json = "{\"accountId\":1,\"name\":\"aProject\"}";

        mockMvc.perform(
                post("/projects")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    Project actual = new ObjectMapper().readValue(
                            mvcResult.getResponse().getContentAsString(), Project.class);
                    assertEquals(actual.getAccountId(), 1);
                    assertEquals(actual.getName(), "aProject");
                })
                .andReturn();
    }

    @Test
    public void testList() throws Exception {
        mockMvc.perform(
                get("/projects")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("accountId", "1"))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    List<Project> projects = new ObjectMapper().readValue(
                            mvcResult.getResponse().getContentAsString(), new TypeReference<List<Project>>() {
                            });
                    Assert.assertEquals(2, projects.size());
                    assertEquals("Flagship", projects.get(0).getName());
                    assertEquals("Hovercraft", projects.get(1).getName());
                });
    }
}