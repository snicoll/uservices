package com.barinek.uservice.dal;

import com.barinek.uservice.models.TimeEntry;
import com.barinek.uservice.support.TestDataSource;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TimeEntryDAOTest {
    @Test
    public void testList() throws Exception {
        TestDataSource.cleanWithFixtures();
        DataSource dataSource = TestDataSource.getDataSource();

        int projectId = 156;
        int anotherProjectId = 157;
        int userId = 106;

        TimeEntryDAO timeEntryDAO = new TimeEntryDAO(dataSource);
        timeEntryDAO.create(new TimeEntry(projectId, userId, new Date(), 5));
        timeEntryDAO.create(new TimeEntry(anotherProjectId, userId, new Date(), 3));

        List<TimeEntry> entries = timeEntryDAO.list(userId);
        assertEquals(2, entries.size());
        assertEquals(projectId, entries.get(0).getProjectId());
        assertEquals(anotherProjectId, entries.get(1).getProjectId());
    }
}