package ch.hcuge.comprehensio.controller;

import ch.hcuge.comprehensio.entity.State;
import ch.hcuge.comprehensio.entity.Transaction;
import ch.hcuge.comprehensio.message.InterpreterTransactionProcessor;
import ch.hcuge.comprehensio.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);


    @Autowired
    private TransactionService transactionService;
    
    @GetMapping
    public ResponseEntity<Iterable<Transaction>> getTransactions() {
        return ResponseEntity.ok(this.transactionService.getTransactions());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable("id")String id) {
    	return transactionService.getTransaction(id).map(ResponseEntity::ok).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
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

//    @GetMapping(path = "/stream-sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public Flux<String> streamFlux() {
//        return Flux.interval(Duration.ofSeconds(1))
//                .map(sequence -> "Flux - " + LocalTime.now().toString());
//    }
//    
//    
//    @GetMapping(path = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public Flux<Transaction> transactionEvent(Principal principal) {
//    	JwtAuthenticationToken auth = ((JwtAuthenticationToken) principal);
////        return transactionService.getTransactions((String) auth.getTokenAttributes().get("sub"));
//    	 return transactionService.getTransactions("1");
//    }
    
    
    
    
    @GetMapping(path = "/sse-interpreter", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Transaction> subscribeInterpreterTransactionMessage() {
        LOGGER.debug("Enter in sse-interpreter");
//        return chatRoomEntry.subscribeSpring5(lastEventId);
    	return transactionService.subscribeTansactionSSEInterpreter();
    }
    
    @GetMapping(path = "/sse-caregiver/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Transaction> subscribeCaregiverTransactionMessage(@PathVariable("id") String transactionId) {
        LOGGER.debug("Enter in sse-caregiver");
    	return transactionService.subscribeTansactionSSECaregiver(transactionId);
    }


    // --------- TEST ----------


    @Autowired
    private InterpreterTransactionProcessor processor;


    @GetMapping(path = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Transaction> receive() {
        return Flux.create(sink -> processor.register(sink::next));
    }

    @PostMapping(path = "/test")
    public ResponseEntity<Void> send() {
        Transaction transaction = Transaction.builder()
                .comment("Hello from backend")
                .id("1")
                .state(State.PENDING)
                .build();
        LOGGER.info("Send new transaction to all consumer '{}", transaction);
        processor.process(transaction);
        return ResponseEntity.noContent().build();
    }
}