package com.barinek.uservices.accounts;

import com.barinek.uservices.schema.TestDataSource;
import com.barinek.uservices.users.User;
import com.barinek.uservices.users.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration
public class RegistrationServiceTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private RegistrationService service;

    @SpringBootApplication
    @ComponentScan(basePackageClasses = {UserRepository.class, RegistrationService.class})
    public static class BasicApp {
    }

    @Test
    public void testCreateUserWithAccount() throws Exception {
        TestDataSource.cleanWithFixtures(dataSource);

        User aUser = service.createUserWithAccount(new User("aUser"));
        assertEquals("aUser", aUser.getName());
    }
}