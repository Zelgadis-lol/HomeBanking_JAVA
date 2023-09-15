package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TransactionRepositoryTest {
    @Autowired
    TransactionRepository transactionRepository;

    @Test
    public void exisTransacction(){
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions,is(not(empty())));
    }
    @Test
    public void hasTransacction(){
        List<Transaction> transactions = transactionRepository.findAll();
        for (Transaction transaction : transactions) {
            assertThat(transaction.getType(), is(not(nullValue())));
        }
    }
}