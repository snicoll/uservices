package com.barinek.uservices.accounts;

import com.barinek.uservices.restsupport.BasicApp;
import com.barinek.uservices.restsupport.RestTestSupport;
import com.barinek.uservices.schema.TestDataSource;
import com.barinek.uservices.users.User;
import com.barinek.uservices.users.UserDAO;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.jetty.server.handler.HandlerList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class AccountControllerTest extends RestTestSupport {
    BasicApp app = new BasicApp() {
        @Override
        public HandlerList getHandlers(Properties properties) throws Exception {
            DataSource ds = TestDataSource.getDataSource();
            HandlerList list = new HandlerList();
            list.addHandler(new AccountController(new AccountDAO(ds)));
            list.addHandler(new RegistrationController(new RegistrationService(new UserDAO(ds), new AccountDAO(ds))));
            return list;
        }
    };

    @Before
    public void setUp() throws Exception {
        app.start();
    }

    @After
    public void tearDown() throws Exception {
        app.stop();
    }

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