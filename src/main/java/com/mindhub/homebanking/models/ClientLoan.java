package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private double amount;
    private int payments;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client clientLoan;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="loan_id")
    private Loan loanClient;

    public ClientLoan() { }

    public ClientLoan(double amount, int payments, Client clientLoan, Loan loanClient) {
        this.amount = amount;
        this.payments = payments;
        this.clientLoan = clientLoan;
        this.loanClient = loanClient;
    }

    public long getId() {
        return id;
    }
    public double getAmount() {
        return amount;
    }
    public int getPayments() {
        return payments;
    }
    public Client getClient() {
        return clientLoan;
    }
    public Loan getLoan() {
        return loanClient;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    public void setPayments(int payments) {
        this.payments = payments;
    }
    public void setClient(Client clientLoan) {
        this.clientLoan = clientLoan;
    }
    public void setLoan(Loan loanClient) {
        this.loanClient = loanClient;
    }

}
