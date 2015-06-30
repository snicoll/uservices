package com.barinek.uservice.allocations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Collection;

@RestController
@RequestMapping("/allocations")
public class AllocationController {
    private final AllocationRepository repository;

    @Autowired
    public AllocationController(AllocationRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.POST)
    Allocation create(@RequestBody Allocation allocation) throws SQLException {
        return repository.create(allocation);
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Allocation> list(@RequestParam int projectId) throws SQLException {
        return repository.list(projectId);
    }
}
