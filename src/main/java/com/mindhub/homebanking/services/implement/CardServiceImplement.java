package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class CardServiceImplement implements CardService {
    @Autowired
    private CardRepository cardRepository;

    @Override
    public List<CardDTO> getCards() {
        return cardRepository.findAll().stream().map(CardDTO::new).collect(toList());
    }

    @Override
    public Card findCardByNumber(String number) {
        return cardRepository.findByNumber(number);
    }

    @Override
    public void saveCard(Card card) {
        cardRepository.save(card);
    }

    @Override
    public ResponseEntity<CardDTO> findById(long id) {
        return cardRepository.findById(id).map(CardDTO::new).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
