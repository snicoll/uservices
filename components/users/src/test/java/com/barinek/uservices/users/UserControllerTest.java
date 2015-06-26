package com.barinek.uservices.users;

import com.barinek.uservices.schema.TestDataSource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration
@WebAppConfiguration
public class UserControllerTest {
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
    public void testShow() throws Exception {
        mockMvc.perform(get("/users").param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    User actual = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), User.class);
                    assertEquals(actual.getName(), "Jack");
                });
    }
}