package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;

    @PostMapping(path = "/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {

        if (firstName.isEmpty())
            return new ResponseEntity<>("First Name empty", HttpStatus.FORBIDDEN);

        if (lastName.isEmpty())
            return new ResponseEntity<>("Last Name empty", HttpStatus.FORBIDDEN);

        if (email.isEmpty())
           return new ResponseEntity<>("Email empty", HttpStatus.FORBIDDEN);

        if (password.isEmpty())
            return new ResponseEntity<>("Password empty", HttpStatus.FORBIDDEN);

        if (clientService.findByEmail(email) !=  null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        Account account = new Account(Utils.getNumberAccount(), LocalDate.now(), 0.0);

        clientService.saveClient(client);

        client.addAccount(account);
        accountService.saveAccount(account);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/clients")
    public List<ClientDTO> getClients() {
        return clientService.getClients();
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<ClientDTO> getClient(Authentication authentication, @PathVariable long id) {
        Client client = clientService.findByEmail(authentication.getName());
        if (id != client.getId())
            return new ResponseEntity<ClientDTO>((ClientDTO) null, HttpStatus.UNAUTHORIZED);

        return clientService.getClient(id);
    }

    @GetMapping("/clients/current")
    public ClientDTO getClient(Authentication authentication) {
        return new ClientDTO(clientService.findByEmail(authentication.getName()));
    }
}
