package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CardService {
    public List<CardDTO> getCards();
    public Card findCardByNumber(String number);
    public void saveCard(Card card);
    public ResponseEntity<CardDTO> findById(long id);
}
