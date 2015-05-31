package com.barinek.uservice.models;

import com.barinek.uservice.dal.AccountDAO;
import com.barinek.uservice.dal.UserDAO;

import java.sql.SQLException;

public class RegistrationService {
    private final UserDAO userDAO;
    private final AccountDAO accountDAO;

    public RegistrationService(UserDAO userDAO, AccountDAO accountDAO) {
        this.userDAO = userDAO;
        this.accountDAO = accountDAO;
    }

    public User createUserWithAccount(User user) throws SQLException {
        User persistedUser = userDAO.create(user); // todo - wrap in transaction
        accountDAO.create(new Account(persistedUser.getId(), String.format("%s's account", user.getName())));
        return persistedUser;
    }
}