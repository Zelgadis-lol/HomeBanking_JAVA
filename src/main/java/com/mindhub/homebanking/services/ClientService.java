package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ClientService {
    public List<ClientDTO> getClients();
    public void saveClient(Client client);
    public ResponseEntity<ClientDTO> getClient(Long id);
    public Client findByEmail(String email);
}
