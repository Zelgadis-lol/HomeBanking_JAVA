package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository) {
		return (args) -> {
			Client cli1 = new Client("Melba", "Morel", "melba@mindhub.com");
			Client cli2 = new Client("Juan", "Perez", "jperez@mindhub.com");

			Account acc1 = new Account("VIN001", LocalDate.now(), 5000.0);
			Account acc2 = new Account("VIN002", LocalDate.now().plusDays(1), 7500.0);

			Account acc3 = new Account("VIN003", LocalDate.now(), -500.0);
			Account acc4 = new Account("VIN004", LocalDate.now().plusDays(1), 17500.0);

			cli1.addAccount(acc1);
			cli1.addAccount(acc2);

			cli2.addAccount(acc3);
			cli2.addAccount(acc4);

			clientRepository.save(cli1);
			clientRepository.save(cli2);

			accountRepository.save(acc1);
			accountRepository.save(acc2);
			accountRepository.save(acc3);
			accountRepository.save(acc4);


		};
	}

}
