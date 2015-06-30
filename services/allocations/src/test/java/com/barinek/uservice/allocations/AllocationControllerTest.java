package com.barinek.uservice.allocations;

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
public class AllocationControllerTest {
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
        String json = "{\"projectId\":2,\"userId\":1,\"firstDay\":\"2015-05-17\",\"lastDay\":\"2015-05-26\"}"; // project and user from fixtures

        mockMvc.perform(
                post("/allocations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(mvcResult -> {
                    Allocation actual = new ObjectMapper()
                            .readValue(mvcResult.getResponse().getContentAsString(), Allocation.class);
                    assertEquals(2, actual.getProjectId());
                    assertEquals(1, actual.getUserId());
                }).andReturn();
    }

    @Test
    public void testList() throws Exception {
        mockMvc.perform(
                get("/allocations")
                        .param("projectId", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    List<Allocation> allocations = new ObjectMapper().readValue(
                            mvcResult.getResponse().getContentAsString(),
                            new TypeReference<List<Allocation>>() {
                            });
                    Allocation allocation = allocations.get(0);
                    assertEquals(1, allocation.getProjectId());
                    assertEquals(1, allocation.getUserId());
                });
    }
}