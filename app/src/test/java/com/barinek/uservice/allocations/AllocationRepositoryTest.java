package com.barinek.uservice.allocations;

import com.barinek.uservice.App;
import com.barinek.uservices.schema.TestDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
public class AllocationRepositoryTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private AllocationRepository dao;

    @Test
    public void testFindFor() throws Exception {
        TestDataSource.cleanWithFixtures(this.dataSource);

        int userId = 138;
        int projectId = 242;
        int anotherProjectId = 243;

        dao.create(new Allocation(projectId, userId, new Date(), new Date()));
        dao.create(new Allocation(anotherProjectId, userId, new Date(), new Date()));

        List<Allocation> allocations = dao.list(projectId);
        assertEquals(1, allocations.size());
        assertEquals(projectId, allocations.get(0).getProjectId());
    }
}