package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomebankingApplication.class, args);
    }

    /*@Autowired
    private PasswordEncoder passwordEnconder;

    @Bean
    public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
        return (args) -> {
            Client cli1 = new Client("Melba", "Morel", "melba@mindhub.com", passwordEnconder.encode("1234"));
            Client cli2 = new Client("Juan", "Perez", "jperez@mindhub.com", passwordEnconder.encode("4321"));
            Client cli3 = new Client("admin", "admin", "admin@mindhub.com", passwordEnconder.encode("123456"));

            Account acc1 = new Account("VIN001", LocalDate.now(), 5000.0);
            Account acc2 = new Account("VIN002", LocalDate.now().plusDays(1), 7500.0);
            Account acc3 = new Account("VIN003", LocalDate.now(), -500.0);
            Account acc4 = new Account("VIN004", LocalDate.now().plusDays(1), 17500.0);

            Transaction tra1 = new Transaction(TransactionType.CREDIT, 200.0, "Credito 1", LocalDateTime.now(), acc1);
            Transaction tra2 = new Transaction(TransactionType.DEBIT, 700.0, "Debito 1", LocalDateTime.now(), acc1);

            Loan loa1 = new Loan("Hipotecario", 500000.0, List.of(12, 24, 36, 48, 60));
            Loan loa2 = new Loan("Personal", 100000.0, List.of(6, 12, 24));
            Loan loa3 = new Loan("Automotriz", 300000.0, List.of(6, 12, 24, 36));

            ClientLoan cl1 = new ClientLoan(400000.00, 60);
            cl1.setClient(cli1);
            cl1.setLoan(loa1);
            ClientLoan cl2 = new ClientLoan(50000.00, 12);
            cl2.setClient(cli1);
            cl2.setLoan(loa2);
            ClientLoan cl3 = new ClientLoan(100000.00, 24);
            cl3.setClient(cli2);
            cl3.setLoan(loa2);
            ClientLoan cl4 = new ClientLoan(200000.00, 36);
            cl4.setClient(cli2);
            cl4.setLoan(loa3);

            Card card1 = new Card(cli1.getFirstName() + ' ' + cli1.getLastName(), CardType.DEBIT, CardColor.GOLD, "122155313153", 123, LocalDate.now().plusYears(5), LocalDate.now());
            Card card2 = new Card(cli1.getFirstName() + ' ' + cli1.getLastName(), CardType.CREDIT, CardColor.TITANIUM, "222155313153", 321, LocalDate.now().plusYears(5), LocalDate.now());
            Card card3 = new Card(cli2.getFirstName() + ' ' + cli2.getLastName(), CardType.CREDIT, CardColor.SILVER, "126768313153", 111, LocalDate.now().plusYears(5), LocalDate.now());

            cli1.addAccount(acc1);
            cli1.addAccount(acc2);
            cli1.addCard(card1);
            cli1.addCard(card2);

            cli2.addAccount(acc3);
            cli2.addAccount(acc4);
            cli2.addCard(card3);

            acc1.addTransaction(tra1);
            acc1.addTransaction(tra2);

            clientRepository.save(cli1);
            clientRepository.save(cli2);
            clientRepository.save(cli3);

            accountRepository.save(acc1);
            accountRepository.save(acc2);
            accountRepository.save(acc3);
            accountRepository.save(acc4);

            transactionRepository.save(tra1);
            transactionRepository.save(tra2);

            loanRepository.save(loa1);
            loanRepository.save(loa2);
            loanRepository.save(loa3);

            clientLoanRepository.save(cl1);
            clientLoanRepository.save(cl2);
            clientLoanRepository.save(cl3);
            clientLoanRepository.save(cl4);

            cardRepository.save(card1);
            cardRepository.save(card2);
            cardRepository.save(card3);
        };
    }*/

}
