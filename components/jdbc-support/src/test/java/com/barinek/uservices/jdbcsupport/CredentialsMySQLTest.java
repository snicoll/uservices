package com.barinek.uservices.jdbcsupport;

import org.junit.Assert;
import org.junit.Test;

public class CredentialsMySQLTest {
    @Test
    public void testFrom() throws Exception {
        String json = "{\"services\":{\"p-mysql\": [{\"credentials\": {\"jdbcUrl\": \"jdbc:mysql://aUrl.com\"}}], \"rediscloud\": [{\"credentials\": {\"hostname\": \"rediscloud.com\",\"password\": \"password\",\"port\": \"1234\"}}]}}";

        CredentialsMySQL credentials = CredentialsMySQL.from(json);

        Assert.assertEquals("jdbc:mysql://aUrl.com", credentials.getUrl());
    }
}