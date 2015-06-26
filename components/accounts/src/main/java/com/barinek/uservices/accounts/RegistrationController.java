package com.barinek.uservices.accounts;

import com.barinek.uservices.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
public class RegistrationController {
    private final RegistrationService service;

    @Autowired
    public RegistrationController(RegistrationService service) {
        this.service = service;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    User create(@RequestBody User user) throws SQLException {
        return service.createUserWithAccount(user);
    }
}
