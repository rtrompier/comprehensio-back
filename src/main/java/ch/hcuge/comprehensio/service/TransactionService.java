package ch.hcuge.comprehensio.service;

import ch.hcuge.comprehensio.entity.Transaction;
import ch.hcuge.comprehensio.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Iterable<Transaction> getTransactions() {
        return this.transactionRepository.findAll();
    }

    public Transaction saveTransaction(Transaction tr) {
        return this.transactionRepository.save(tr);
    }
}
