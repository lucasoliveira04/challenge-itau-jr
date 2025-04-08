package com.challengeitau.challengeitaujunior.modal;

import com.challengeitau.challengeitaujunior.respository.IDatabase;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Getter @Setter
public class TransactionRepositoryInMemory implements IDatabase<Transaction> {
    private List<Transaction> transactions = new ArrayList<>();

    @Override
    public void save(Transaction object) {
        transactions.add(object);
    }

    @Override
    public List<Transaction> findAll() {
        return transactions;
    }

    @Override
    public void delete() {
        transactions.clear();
    }
}
