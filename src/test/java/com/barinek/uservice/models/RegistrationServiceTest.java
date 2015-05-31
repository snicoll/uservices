package com.barinek.uservice.models;

import com.barinek.uservice.dal.AccountDAO;
import com.barinek.uservice.dal.UserDAO;
import com.barinek.uservice.support.TestDataSource;
import org.junit.Test;

import javax.sql.DataSource;

import static org.junit.Assert.assertEquals;

public class RegistrationServiceTest {
    @Test
    public void testCreateUserWithAccount() throws Exception {
        TestDataSource.cleanWithFixtures();
        DataSource dataSource = TestDataSource.getDataSource();

        RegistrationService service = new RegistrationService(new UserDAO(dataSource), new AccountDAO(dataSource));
        User aUser = service.createUserWithAccount(new User("aUser"));

        assertEquals("aUser", aUser.getName());
    }
}