package com.barinek.uservices.accounts;

import com.barinek.uservices.schema.TestDataSource;
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
public class AccountRepositoryTest {

    @SpringBootApplication
    @ComponentScan(basePackageClasses = {UserRepository.class, AccountRepository.class})
    public static class BasicApp {
    }

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private DataSource dataSource;

    @Test
    public void testFindFor() throws Exception {
        TestDataSource.cleanWithFixtures(dataSource);

        int ownerId = 138;

        accountRepository.create(new Account(ownerId, "anAccount"));
        Account anAccount = accountRepository.findFor(ownerId);
        assertEquals(ownerId, anAccount.getOwnerId());
    }
}