package com.barinek.uservice.backlog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Collection;

@RestController
@RequestMapping("/stories")
public class StoryController {
    private final StoryRepository dao;

    @Autowired
    public StoryController(StoryRepository dao) {
        this.dao = dao;
    }

    @RequestMapping(method = RequestMethod.POST)
    Story create(@RequestBody Story s) throws Exception {
        return dao.create(s);
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Story> list(@RequestParam int projectId) throws SQLException {
        return dao.list(projectId);
    }
}