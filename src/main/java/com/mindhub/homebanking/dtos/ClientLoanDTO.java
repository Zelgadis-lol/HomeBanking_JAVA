package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;

public class ClientLoanDTO {

    private long id;
    private double amount;
    private int payments;
    private Client clientLoan;
    private Loan loanClient;

    public ClientLoanDTO(ClientLoan clientLoan) {
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
        this.clientLoan = clientLoan.getClient();
        this.loanClient = clientLoan.getLoan();
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
}
