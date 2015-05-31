package com.barinek.uservices.accounts;

import com.barinek.uservices.schema.TestDataSource;
import org.junit.Test;

import javax.sql.DataSource;

import static junit.framework.TestCase.assertEquals;

public class AccountDAOTest {
    @Test
    public void testFindFor() throws Exception {
        TestDataSource.cleanWithFixtures();
        DataSource dataSource = TestDataSource.getDataSource();

        int ownerId = 138;

        AccountDAO accountDAO = new AccountDAO(dataSource);
        accountDAO.create(new Account(ownerId, "anAccount"));
        Account anAccount = accountDAO.findFor(ownerId);

        assertEquals(ownerId, anAccount.getOwnerId());
    }
}