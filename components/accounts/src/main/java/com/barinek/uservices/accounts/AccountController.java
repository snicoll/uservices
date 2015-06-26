package com.barinek.uservices.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    private final AccountRepository repository;

    @Autowired
    public AccountController(AccountRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/accounts")
    Account list(@RequestParam int ownerId) throws Exception {
        return repository.findFor(ownerId);
    }
}