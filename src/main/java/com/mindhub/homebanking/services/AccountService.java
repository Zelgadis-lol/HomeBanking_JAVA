package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AccountService {
    public List<AccountDTO> getAccounts();
    public void saveAccount(Account account);
    public ResponseEntity<AccountDTO> findById(long id);
    public Account findByNumber(String number);
}
