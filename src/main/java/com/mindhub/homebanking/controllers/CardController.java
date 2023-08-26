package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/cards")
    public List<CardDTO> getCards() {
        return cardRepository.findAll().stream().map(CardDTO::new).collect(toList());
    }

    @RequestMapping("/cards/{id}")
    public ResponseEntity<CardDTO> getCard(Authentication authentication, @PathVariable long id) {
        Client client = clientRepository.findByEmail(authentication.getName());
        if (id != client.getId())
            return new ResponseEntity<CardDTO>((CardDTO) null, HttpStatus.UNAUTHORIZED);

        return cardRepository.findById(id).map(CardDTO::new).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(path = "clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> createCard(Authentication authentication,
                               @RequestParam CardType cardType, @RequestParam CardColor cardColor) {

        Client client = clientRepository.findByEmail(authentication.getName());

        if(authentication.getAuthorities().stream().noneMatch(ga -> ga.getAuthority().equals("CLIENT")))
            return new ResponseEntity<>("The User is not a CLIENT", HttpStatus.FORBIDDEN);

        if(client.getCards().stream().filter(gc -> gc.getType().equals(cardType)).count() >= 3)
            return new ResponseEntity<>("3 "+cardType+" Cards max", HttpStatus.FORBIDDEN);

        String cardNumber = Utils.getNumberCard();
        int cvv = Utils.getNumberCVV();

        LocalDate thruDate = LocalDate.now();
        LocalDate fromDate = thruDate.plusYears(5);

        Card card1 = new Card(client.getFirstName() + " " + client.getLastName(), cardType, cardColor, cardNumber, cvv, fromDate, thruDate);
        cardRepository.save(card1);

        client.addCard(card1);
        clientRepository.save(client);

        return new ResponseEntity<>("Card created", HttpStatus.CREATED);
    }

}
