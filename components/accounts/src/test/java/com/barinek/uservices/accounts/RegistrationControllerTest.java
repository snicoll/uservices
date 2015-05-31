package com.barinek.uservices.accounts;

import com.barinek.uservices.restsupport.BasicApp;
import com.barinek.uservices.restsupport.RestTestSupport;
import com.barinek.uservices.schema.TestDataSource;
import com.barinek.uservices.users.User;
import com.barinek.uservices.users.UserDAO;
import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.jetty.server.handler.HandlerList;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.Properties;

public class RegistrationControllerTest extends RestTestSupport {
    BasicApp app = new BasicApp() {
        @Override
        public HandlerList getHandlers(Properties properties) throws Exception {
            DataSource ds = TestDataSource.getDataSource();
            HandlerList list = new HandlerList();
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
    public void testRegister() throws Exception {
        TestDataSource.cleanWithFixtures();

        String json = "{\"name\":\"aUser\"}";
        String response = doPost("http://localhost:8080/registration", json);

        User actual = new ObjectMapper().readValue(response, User.class);
        Assert.assertEquals("aUser", actual.getName());
    }
}