package com.barinek.uservices.accounts;

import com.barinek.uservices.schema.TestDataSource;
import com.barinek.uservices.users.User;
import com.barinek.uservices.users.UserDAO;
import org.junit.Assert;
import org.junit.Test;

import javax.sql.DataSource;

public class RegistrationServiceTest {
    @Test
    public void testCreateUserWithAccount() throws Exception {
        TestDataSource.cleanWithFixtures();
        DataSource dataSource = TestDataSource.getDataSource();

        RegistrationService service = new RegistrationService(new UserDAO(dataSource), new AccountDAO(dataSource));
        User aUser = service.createUserWithAccount(new User("aUser"));

        Assert.assertEquals("aUser", aUser.getName());
    }
}