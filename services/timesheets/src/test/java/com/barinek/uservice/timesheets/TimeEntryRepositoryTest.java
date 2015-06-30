package com.barinek.uservice.timesheets;

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
public class TimeEntryRepositoryTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private TimeEntryRepository timeEntryRepository;

    @Test
    public void testList() throws Exception {
        TestDataSource.cleanWithFixtures(this.dataSource);

        int projectId = 156;
        int anotherProjectId = 157;
        int userId = 106;

        timeEntryRepository.create(new TimeEntry(projectId, userId, new Date(), 5));
        timeEntryRepository.create(new TimeEntry(anotherProjectId, userId, new Date(), 3));

        List<TimeEntry> entries = timeEntryRepository.list(userId);
        assertEquals(2, entries.size());
        assertEquals(projectId, entries.get(0).getProjectId());
        assertEquals(anotherProjectId, entries.get(1).getProjectId());
    }
}