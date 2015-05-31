package com.barinek.uservices.restsupport;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.net.URISyntaxException;

public class RestTestSupport {
    public String doGet(String endpoint, BasicNameValuePair... pairs) throws java.io.IOException, URISyntaxException {
        URIBuilder builder = new URIBuilder(endpoint);
        for (BasicNameValuePair pair : pairs) {
            builder.addParameter(pair.getName(), pair.getValue());
        }
        HttpGet get = new HttpGet(builder.build());
        HttpClient client = new DefaultHttpClient();
        ResponseHandler<String> handler = new BasicResponseHandler();
        return client.execute(get, handler);
    }

    public String doPost(String endpoint, String data) throws java.io.IOException {
        HttpPost post = new HttpPost(endpoint);
        post.addHeader("Content-type", "application/json");
        post.setEntity(new StringEntity(data));

        HttpClient client = new DefaultHttpClient();
        ResponseHandler<String> handler = new BasicResponseHandler();
        return client.execute(post, handler);
    }
}