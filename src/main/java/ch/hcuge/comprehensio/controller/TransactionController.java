package ch.hcuge.comprehensio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.hcuge.comprehensio.entity.Transaction;
import ch.hcuge.comprehensio.service.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public ResponseEntity<Iterable<Transaction>> getTransactions() {
        return ResponseEntity.ok(this.transactionService.getTransactions());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Transaction> saveTransaction(@RequestBody Transaction transaction) {
        Transaction tr = this.transactionService.saveTransaction(transaction);
        return ResponseEntity.ok(tr);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Transaction> endTransaction(@PathVariable("id") String id, @RequestBody Transaction transaction) {
        Transaction tr = this.transactionService.saveTransaction(transaction);
        return ResponseEntity.ok(tr);
    }
}
