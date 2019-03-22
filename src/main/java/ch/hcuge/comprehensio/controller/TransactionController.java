package ch.hcuge.comprehensio.controller;

import ch.hcuge.comprehensio.entity.Transaction;
import ch.hcuge.comprehensio.entity.User;
import ch.hcuge.comprehensio.service.TransactionService;
import ch.hcuge.comprehensio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public ResponseEntity<Iterable<Transaction>> getTransactions() {
        return ResponseEntity.ok(this.transactionService.getTransactions());
    }

    @PostMapping
    public ResponseEntity<Transaction> saveTransaction(@RequestBody Transaction transaction) {
        Transaction tr = this.transactionService.saveTransaction(transaction);
        return ResponseEntity.ok(tr);
    }

}
