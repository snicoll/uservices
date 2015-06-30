package com.barinek.uservice.timesheets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Collection;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {
    private final TimeEntryRepository dao;

    @Autowired
    public TimeEntryController(TimeEntryRepository dao) {
        this.dao = dao;
    }

    @RequestMapping(method = RequestMethod.POST)
    TimeEntry create(@RequestBody TimeEntry timeEntry) throws SQLException {
        return dao.create(timeEntry);
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<TimeEntry> list(@RequestParam int userId) throws SQLException {
        return dao.list(userId);
    }
}
