package com.barinek.uservices.accounts;

import com.barinek.uservices.users.User;
import com.barinek.uservices.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
public class RegistrationService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public RegistrationService(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional // fixed
    public User createUserWithAccount(User user) throws SQLException {
        User persistedUser = userRepository.create(user);
        accountRepository.create(new Account(persistedUser.getId(), String.format("%s's account", user.getName())));
        return persistedUser;
    }
}