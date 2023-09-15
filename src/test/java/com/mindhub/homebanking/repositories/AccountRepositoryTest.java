package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountRepositoryTest {
    @Autowired
    AccountRepository accountRepository;

    @Test
    public void existAccounts(){
        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts, is(not(empty())));
    }
}