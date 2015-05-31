package com.barinek.uservice.users;

import com.barinek.uservice.AppRunner;
import com.barinek.uservice.TestDataSource;
import com.barinek.uservice.users.User;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserControllerTest extends AppRunner {
    @Test
    public void testShow() throws Exception {
        TestDataSource.cleanWithFixtures();

        String response = doGet("http://localhost:8080/users", new BasicNameValuePair("userId", "1")); // user from fixtures

        User actual = new ObjectMapper().readValue(response, User.class);
        assertEquals(actual.getName(), actual.getName());
    }
}