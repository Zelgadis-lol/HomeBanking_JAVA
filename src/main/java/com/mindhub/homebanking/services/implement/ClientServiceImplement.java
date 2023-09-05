package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImplement implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public List<ClientDTO> getClients() {
        return clientRepository.findAll().stream().map(ClientDTO::new).collect(Collectors.toList());
    }

    @Override
    public void saveClient(Client client) {
        clientRepository.save(client);
    }

    @Override
    public ResponseEntity<ClientDTO> getClient(Long id) {
        return clientRepository.findById(id).map(ClientDTO::new).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

}
