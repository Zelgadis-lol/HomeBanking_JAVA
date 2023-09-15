package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
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
    private CardService cardService;
    @Autowired
    private ClientService clientService;

    @GetMapping("/cards")
    public List<CardDTO> getCards() {
        return cardService.getCards();
    }

    @PostMapping(path = "clients/current/cards")
    public ResponseEntity<Object> createCard(Authentication authentication,
                               @RequestParam CardType cardType, @RequestParam CardColor cardColor) {

        if (cardType == null)
            return new ResponseEntity<>("Card type empty", HttpStatus.FORBIDDEN);

        if (cardColor == null)
            return new ResponseEntity<>("Card color empty", HttpStatus.FORBIDDEN);

        Client client = clientService.findByEmail(authentication.getName());

        if(authentication.getAuthorities().stream().noneMatch(ga -> ga.getAuthority().equals("CLIENT")))
            return new ResponseEntity<>("The User is not a CLIENT", HttpStatus.FORBIDDEN);

        if(client.getCards().stream().filter(gc -> gc.getType().equals(cardType)).filter(Card::isActive).count() >= 3)
            return new ResponseEntity<>("3 "+cardType+" Cards max", HttpStatus.FORBIDDEN);

        String cardNumber;
        do {
            cardNumber = Utils.getNumberCard();
        }while (cardService.findCardByNumber(cardNumber) != null);

        int cvv = Utils.getNumberCVV();

        LocalDate thruDate = LocalDate.now();
        LocalDate fromDate = thruDate.plusYears(5);

        Card card1 = new Card(client.getFirstName() + " " + client.getLastName(), cardType, cardColor, cardNumber, cvv, fromDate, thruDate, true);
        cardService.saveCard(card1);

        client.addCard(card1);
        clientService.saveClient(client);

        return new ResponseEntity<>("Card created", HttpStatus.CREATED);
    }

    @PatchMapping("clients/current/cards/{id}")
    public ResponseEntity<Object> deleteCard(@PathVariable long id, Authentication authentication){
        Client client = clientService.findByEmail(authentication.getName());
        Card card = cardService.findById(id);

        boolean hasCard = client.getCards().contains(card);

        if(card == null){
            return new ResponseEntity<>("Card not found", HttpStatus.FORBIDDEN);
        }

        if(!hasCard){
            return new ResponseEntity<>("Card dont belong to authenticated client", HttpStatus.FORBIDDEN);
        }

        card.setActive(false);
        cardService.saveCard(card);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
