package com.barinek.uservices.users;

import com.barinek.uservices.schema.TestDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration
public class UserRepositoryTest {

    @Autowired
    private DataSource dataSource;

    @SpringBootApplication
    public static class BasicApp {
    }

    @Test
    public void testShow() throws Exception {
        TestDataSource.cleanWithFixtures(dataSource);

        UserRepository userRepository = new UserRepository(new JdbcTemplate(dataSource));
        User aUser = userRepository.create(new User("aUser"));
        User actual = userRepository.show(aUser.getId());

        assertEquals("aUser", actual.getName());
    }
}