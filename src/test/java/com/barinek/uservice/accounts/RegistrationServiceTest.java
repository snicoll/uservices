package com.barinek.uservice.accounts;

import com.barinek.uservice.TestDataSource;
import com.barinek.uservice.accounts.AccountDAO;
import com.barinek.uservice.accounts.RegistrationService;
import com.barinek.uservice.users.User;
import com.barinek.uservice.users.UserDAO;
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