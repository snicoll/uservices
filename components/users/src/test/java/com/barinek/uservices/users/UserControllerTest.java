package com.barinek.uservices.users;

import com.barinek.uservices.restsupport.BasicApp;
import com.barinek.uservices.restsupport.RestTestSupport;
import com.barinek.uservices.schema.TestDataSource;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.jetty.server.handler.HandlerList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class UserControllerTest extends RestTestSupport {
    BasicApp app = new BasicApp() {
        @Override
        public HandlerList getHandlers(Properties properties) throws Exception {
            HandlerList list = new HandlerList();
            list.addHandler(new UserController(new UserDAO(TestDataSource.getDataSource())));
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

        String response = doGet("http://localhost:8080/users", new BasicNameValuePair("userId", "1")); // user from fixtures

        User actual = new ObjectMapper().readValue(response, User.class);
        assertEquals(actual.getName(), actual.getName());
    }
}