package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class AccountServiceImplement implements AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<AccountDTO> getAccounts() {
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }

    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public ResponseEntity<AccountDTO> findById(long id) {
        return accountRepository.findById(id).map(AccountDTO::new).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public Account findByNumber(String number) {
        return accountRepository.findByNumber(number);
    }
}
