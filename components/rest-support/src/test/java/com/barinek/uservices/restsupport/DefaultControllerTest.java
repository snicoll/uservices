package com.barinek.uservices.restsupport;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.eclipse.jetty.server.handler.HandlerList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class DefaultControllerTest {
    BasicApp app = new BasicApp() {
        @Override
        public HandlerList getHandlers(Properties properties) throws Exception {
            HandlerList list = new HandlerList();
            list.addHandler(new DefaultController());
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
    public void testNoop() throws Exception {
        HttpGet get = new HttpGet("http://localhost:8080");
        HttpClient client = new DefaultHttpClient();
        ResponseHandler<String> response = new BasicResponseHandler();
        String responseBody = client.execute(get, response);
        assertEquals("Noop!", responseBody);
    }
}