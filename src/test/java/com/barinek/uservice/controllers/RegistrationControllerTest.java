package com.barinek.uservice.controllers;

import com.barinek.uservice.models.User;
import com.barinek.uservice.support.AppRunner;
import com.barinek.uservice.support.TestDataSource;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RegistrationControllerTest extends AppRunner {
    @Test
    public void testRegister() throws Exception {
        TestDataSource.cleanWithFixtures();

        String json = "{\"name\":\"aUser\"}";
        String response = doPost("http://localhost:8080/registration", json);

        User actual = new ObjectMapper().readValue(response, User.class);
        assertEquals("aUser", actual.getName());
    }
}