package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String cardholder;
    private CardType type;
    private CardColor color;
    private String number;
    private int cvv;
    private LocalDate thruDate;
    private LocalDate fromDate;
    private boolean isActive;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    public Card() { }

    public Card(String cardholder, CardType type, CardColor color, String number, int cvv, LocalDate thruDate, LocalDate fromDate, boolean isActive) {
        this.cardholder = cardholder;
        this.type = type;
        this.color = color;
        this.number = number;
        this.cvv = cvv;
        this.thruDate = thruDate;
        this.fromDate = fromDate;
        this.isActive = isActive;
    }

    public long getId() {return id;}
    public String getCardholder() {return cardholder;}
    public CardType getType() {return type;}
    public CardColor getColor() {return color;}
    public String getNumber() {return number;}
    public int getCvv() {return cvv;}
    public LocalDate getThruDate() {return thruDate;}
    public LocalDate getFromDate() {return fromDate;}
    public boolean isActive() {return isActive;}
    @JsonIgnore
    public Client getClient() {return client;}


    public void setCardholder(String cardholder) {this.cardholder = cardholder;}
    public void setType(CardType type) {this.type = type;}
    public void setColor(CardColor color) {this.color = color;}
    public void setNumber(String number) {this.number = number;}
    public void setCvv(int cvv) {this.cvv = cvv;}
    public void setThruDate(LocalDate thruDate) {this.thruDate = thruDate;}
    public void setFromDate(LocalDate fromDate) {this.fromDate = fromDate;}
    public void setClient(Client client) {this.client = client;}
    public void setActive(boolean active) {isActive = active;}
}
