package com.barinek.uservice.accounts;

import com.barinek.uservice.AppRunner;
import com.barinek.uservice.TestDataSource;
import com.barinek.uservice.accounts.Account;
import com.barinek.uservice.users.User;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountControllerTest extends AppRunner {
    @Test
    public void testShow() throws Exception {
        TestDataSource.cleanWithFixtures();

        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(doPost("http://localhost:8080/registration", "{\"name\":\"aUser\"}"), User.class);

        BasicNameValuePair ownerId = new BasicNameValuePair("ownerId", String.valueOf(user.getId()));
        Account actual = objectMapper.readValue(doGet("http://localhost:8080/accounts", ownerId), Account.class);

        assertEquals(user.getId(), actual.getOwnerId());
        assertEquals("aUser's account", actual.getName());
    }
}