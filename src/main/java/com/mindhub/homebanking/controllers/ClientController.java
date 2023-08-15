package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/clients")
    public List<ClientDTO> getClients() {
        return clientRepository.findAll()
                .stream()
                .map(ClientDTO::new)
                .collect(toList());
    }

    @RequestMapping("/clients/{id}")
    public ResponseEntity<ClientDTO> getClient(@PathVariable long id) {
        return clientRepository.findById(id).map(ClientDTO::new).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

}
