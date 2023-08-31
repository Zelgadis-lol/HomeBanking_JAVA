package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;


    @Transactional
    @PostMapping (path = "/transactions")
    public ResponseEntity<Object> createdTransaction(Authentication authentication,
                                                     @RequestParam Double amount, @RequestParam String description,
                                                     @RequestParam String fromAccountNumber, @RequestParam String toAccountNumber) {

        if (amount.isNaN())
            return new ResponseEntity<>("Amount empty", HttpStatus.FORBIDDEN);

        if (description.isEmpty())
            return new ResponseEntity<>("Description empty", HttpStatus.FORBIDDEN);

        if (fromAccountNumber.isEmpty())
            return new ResponseEntity<>("Account Origin empty", HttpStatus.FORBIDDEN);

        if (toAccountNumber.isEmpty())
            return new ResponseEntity<>("Account Destiny empty", HttpStatus.FORBIDDEN);

        if (fromAccountNumber.equals(toAccountNumber))
            return new ResponseEntity<>("The Accounts are the same", HttpStatus.FORBIDDEN);

        Account accountOrigin=accountRepository.findByNumber(fromAccountNumber);
        if (accountOrigin == null)
            return new ResponseEntity<>("Account Origin not exists",HttpStatus.FORBIDDEN);

        Account accountDestiny=accountRepository.findByNumber(toAccountNumber);
        if (accountDestiny == null)
            return new ResponseEntity<>("Account Destiny not exists",HttpStatus.FORBIDDEN);

        Client client = clientRepository.findByEmail(authentication.getName());

        Set<Account> setClientAccounts= client.getAccounts();
        if(setClientAccounts.stream().noneMatch(gc -> gc.getNumber().equals(fromAccountNumber)) )
            return new ResponseEntity<>("Origin Account not valid", HttpStatus.FORBIDDEN);

        if (accountOrigin.getBalance() < amount || amount <= 0)
            return new ResponseEntity<>("Insufficient amount",HttpStatus.FORBIDDEN);

        Transaction tra1 = new Transaction(TransactionType.DEBIT, amount, description, LocalDateTime.now(), accountOrigin);
        Transaction tra2 = new Transaction(TransactionType.CREDIT, amount, description, LocalDateTime.now(), accountDestiny);
        transactionRepository.save(tra1);
        transactionRepository.save(tra2);

        double balOrigin = accountOrigin.getBalance() - amount;
        double balDestiny = accountDestiny.getBalance() + amount;

        accountOrigin.setBalance(balOrigin);
        accountDestiny.setBalance(balDestiny);


        return new ResponseEntity<>("Transfer success",HttpStatus.CREATED);
    }

}
