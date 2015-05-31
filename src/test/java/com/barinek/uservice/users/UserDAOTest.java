package com.barinek.uservice.users;

import com.barinek.uservice.TestDataSource;
import com.barinek.uservice.users.User;
import com.barinek.uservice.users.UserDAO;
import org.junit.Test;

import javax.sql.DataSource;

import static org.junit.Assert.assertEquals;

public class UserDAOTest {
    @Test
    public void testShow() throws Exception {
        TestDataSource.cleanWithFixtures();
        DataSource dataSource = TestDataSource.getDataSource();

        UserDAO userDAO = new UserDAO(dataSource);
        User aUser = userDAO.create(new User("aUser"));
        User actual = userDAO.show(aUser.getId());

        assertEquals("aUser", actual.getName());
    }
}