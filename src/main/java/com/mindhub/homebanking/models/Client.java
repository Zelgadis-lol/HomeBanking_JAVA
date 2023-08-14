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

    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    Set<Account> accounts = new HashSet<>();

    @OneToMany(mappedBy="client", fetch=FetchType.EAGER) //ver aca el mapped
    Set<ClientLoan> clientLoans = new HashSet<>();

    public Client() { }
    public Client(String first, String last, String mail) {
        this.firstName = first;
        this.lastName = last;
        this.email = mail;
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
    public Set<Account> getAccounts() {
        return accounts;
    }
    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    public List<Loan> getLoans() {
        return clientLoans.stream().map(sub -> sub.getLoan()).collect(toList());
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
    public String toString() {
        return firstName + " " + lastName;
    }

    public void addAccount(Account account) {
        account.setClient(this);
        accounts.add(account);
    }
}