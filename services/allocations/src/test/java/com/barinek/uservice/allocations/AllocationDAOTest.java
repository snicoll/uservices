package com.barinek.uservice.allocations;

import com.barinek.uservices.schema.TestDataSource;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AllocationDAOTest {
    @Test
    public void testFindFor() throws Exception {
        TestDataSource.cleanWithFixtures();
        DataSource dataSource = TestDataSource.getDataSource();

        int userId = 138;
        int projectId = 242;
        int anotherProjectId = 243;

        AllocationDAO dao = new AllocationDAO(dataSource);
        dao.create(new Allocation(projectId, userId, new Date(), new Date()));
        dao.create(new Allocation(anotherProjectId, userId, new Date(), new Date()));

        List<Allocation> allocations = dao.list(projectId);
        assertEquals(1, allocations.size());
        assertEquals(projectId, allocations.get(0).getProjectId());
    }
}