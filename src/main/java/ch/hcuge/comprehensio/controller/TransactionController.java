package ch.hcuge.comprehensio.controller;

import java.security.Principal;
import java.time.Duration;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.hcuge.comprehensio.entity.Transaction;
import ch.hcuge.comprehensio.service.TransactionService;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public ResponseEntity<Iterable<Transaction>> getTransactions() {
        return ResponseEntity.ok(this.transactionService.getTransactions());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransaction(String id) {
        return ResponseEntity.ok(Transaction.builder().build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Transaction> saveTransaction(@RequestBody Transaction transaction) {
        Transaction tr = this.transactionService.saveTransaction(transaction);
        return ResponseEntity.ok(tr);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable("id") String id, @RequestBody Transaction transaction) {
        Transaction tr = this.transactionService.updateTransaction(transaction);
        return ResponseEntity.ok(tr);
    }
    
    @GetMapping("/stream-sse")
    public Flux<ServerSentEvent<String>> streamEvents() {
        return Flux.interval(Duration.ofSeconds(1))
          .map(sequence -> ServerSentEvent.<String> builder()
            .id(String.valueOf(sequence))
              .event("periodic-event")
              .data("SSE - " + LocalTime.now().toString())
              .build());
    }
    
    
    @GetMapping("/sse")
    public Flux<Transaction> transactionEvent(Principal principal) {
    	JwtAuthenticationToken auth = ((JwtAuthenticationToken) principal);
//        return transactionService.getTransactions((String) auth.getTokenAttributes().get("sub"));
    	 return transactionService.getTransactions("1");
    }
}
