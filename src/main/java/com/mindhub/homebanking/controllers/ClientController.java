package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
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
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(path = "/clients", method = RequestMethod.POST)
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

        if (clientRepository.findByEmail(email) !=  null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        Account account = new Account(Utils.getNumberAccount(), LocalDate.now(), 0.0);

        clientRepository.save(client);

        client.addAccount(account);
        accountRepository.save(account);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping("/clients")
    public List<ClientDTO> getClients() {
        return clientRepository.findAll()
                .stream()
                .map(ClientDTO::new)
                .collect(toList());
    }

    @RequestMapping("/clients/{id}")
    public ResponseEntity<ClientDTO> getClient(Authentication authentication, @PathVariable long id) {
        Client client = clientRepository.findByEmail(authentication.getName());
        if (id != client.getId())
            return new ResponseEntity<ClientDTO>((ClientDTO) null, HttpStatus.UNAUTHORIZED);

        return clientRepository.findById(id).map(ClientDTO::new).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping("/clients/current")
    public ClientDTO getClient(Authentication authentication) {
        return new ClientDTO(clientRepository.findByEmail(authentication.getName()));
    }
}
