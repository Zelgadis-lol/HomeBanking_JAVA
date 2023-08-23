package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();
    @OneToMany(mappedBy="client", fetch=FetchType.EAGER) //ver aca el mapped
    private Set<ClientLoan> clientLoans = new HashSet<>();
    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();

    public Client() { }
    public Client(String first, String last, String mail, String password) {
        this.firstName = first;
        this.lastName = last;
        this.email = mail;
        this.password = password;
    }

    public long getId() { return id;}
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public Set<Account> getAccounts() {
        return accounts;
    }
    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }
    public Set<Card> getCards() {
        return cards;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }
    public void setClientLoans(Set<ClientLoan> clientLoans) {
        this.clientLoans = clientLoans;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public void addAccount(Account account) {
        account.setClient(this);
        accounts.add(account);
    }

    public void addCard(Card card) {
        card.setClient(this);
        cards.add(card);
    }
}