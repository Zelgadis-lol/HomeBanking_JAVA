package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.ClientLoanRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.LoanService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    AccountService accountService;
    @Autowired
    ClientService clientService;
    @Autowired
    TransactionService transactionService;
    @Autowired
    LoanService loanService;
    @Autowired
    ClientLoanRepository clientLoanRepository;

    @GetMapping("/loans")
    public List<LoanDTO> getLoans(){
        return loanService.getLoans();
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> newLoan(@RequestBody LoanApplicationDTO loanApplicationDTO,
                                          Authentication authentication){

        double amount = loanApplicationDTO.getAmount();
        Integer payments = loanApplicationDTO.getPayments();

        if(amount == 0 || payments == null){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if(amount < 0){
            return new ResponseEntity<>("Amount cant be negative", HttpStatus.FORBIDDEN);
        }

        Loan loan = loanService.findById(loanApplicationDTO.getLoanId());
        if(loan == null){
            return new ResponseEntity<>("Loan not exist", HttpStatus.FORBIDDEN);
        }

        if(loan.getMaxAmount() < amount){
            return new ResponseEntity<>("Amount over loan max amount", HttpStatus.FORBIDDEN);
        }

        if(!loan.getPayments().contains(payments)){
            return new ResponseEntity<>("Not available", HttpStatus.FORBIDDEN);
        }

        Account account = accountService.findByNumber(loanApplicationDTO.getToAccountNumber());
        if(account == null){
            return new ResponseEntity<>("Destiny account not exist", HttpStatus.FORBIDDEN);
        }

        Client client = clientService.findByEmail(authentication.getName());
        boolean belongsToClient = client.getAccounts().contains(account);
        if(!belongsToClient){
            return new ResponseEntity<>("Destiny account not belong to authenticated client", HttpStatus.FORBIDDEN);
        }

        account.setBalance(account.getBalance() + amount);

        ClientLoan cl1 = new ClientLoan(amount * 1.2, payments);
        clientLoanRepository.save(cl1);
        cl1.setClient(client);
        cl1.setLoan(loan);

        clientService.saveClient(client);
        loanService.saveLoan(loan);

        transactionService.saveTransaction(new Transaction(TransactionType.CREDIT, amount, "Loan approved", LocalDateTime.now(), account));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
