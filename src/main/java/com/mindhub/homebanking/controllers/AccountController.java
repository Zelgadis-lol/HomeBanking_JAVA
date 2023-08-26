package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/accounts")
    public List<AccountDTO> getAccounts() {
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }

    @RequestMapping("/accounts/{id}")
    public ResponseEntity<AccountDTO> getAccount(Authentication authentication, @PathVariable long id) {
        Client client = clientRepository.findByEmail(authentication.getName());
        if (id != client.getId())
            return new ResponseEntity<AccountDTO>((AccountDTO) null, HttpStatus.UNAUTHORIZED);

        return accountRepository.findById(id).map(AccountDTO::new).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> newAccount(Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());

        if(authentication.getAuthorities().stream().noneMatch(ga -> ga.getAuthority().equals("CLIENT")))
            return new ResponseEntity<>("The User is not a CLIENT", HttpStatus.FORBIDDEN);

        if(client.getAccounts().size() >= 3)
            return new ResponseEntity<>("3 Accounts max", HttpStatus.FORBIDDEN);

        Account account = new Account(Utils.getNumberAccount(), LocalDate.now(), 0.0);

        client.addAccount(account);
        accountRepository.save(account);
        clientRepository.save(client);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
