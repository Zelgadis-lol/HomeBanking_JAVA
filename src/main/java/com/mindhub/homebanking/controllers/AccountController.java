package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts() {
        return accountService.getAccounts();
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<AccountDTO> getAccount(Authentication authentication, @PathVariable long id) {
        Client client = clientService.findByEmail(authentication.getName());
        if (client.getAccounts().stream().noneMatch(gc -> gc.getId() == id))
            return new ResponseEntity<AccountDTO>((AccountDTO) null, HttpStatus.UNAUTHORIZED);

        return accountService.findById(id);
    }

    @GetMapping("clients/current/accounts")
    public List<AccountDTO> getAccountDTO(Authentication authentication){
        Client client = clientService.findByEmail(authentication.getName());
        return client.getAccounts().stream().map(AccountDTO::new).collect(toList());
                //accountRepository.findAll().stream().map(AccountDTO::new).collect(Collectors.toList());
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> newAccount(Authentication authentication) {
        Client client = clientService.findByEmail(authentication.getName());

        if(authentication.getAuthorities().stream().noneMatch(ga -> ga.getAuthority().equals("CLIENT")))
            return new ResponseEntity<>("The User is not a CLIENT", HttpStatus.FORBIDDEN);

        if(client.getAccounts().size() >= 3)
            return new ResponseEntity<>("3 Accounts max", HttpStatus.FORBIDDEN);

        String accountNumber;
        do {
            accountNumber = Utils.getNumberAccount();
        }while(accountService.findByNumber(accountNumber) != null);

        Account account = new Account(accountNumber, LocalDate.now(), 0.0);

        client.addAccount(account);
        accountService.saveAccount(account);
        clientService.saveClient(client);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
