package ch.hcuge.comprehensio.controller;

import io.netty.channel.ChannelOutboundBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.hcuge.comprehensio.entity.Transaction;
import ch.hcuge.comprehensio.service.TransactionService;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ReplayProcessor;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

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
    public Flux<ServerSentEvent<Transaction>> subscribeInterpreterTransactionMessage() {
        LOGGER.debug("Enter in sse-interpreter");
//        return chatRoomEntry.subscribeSpring5(lastEventId);
    	return transactionService.subscribeTansactionSSEInterpreter();
    }
    
    @GetMapping(path = "/sse-caregiver/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<Transaction>> subscribeCaregiverTransactionMessage(@PathVariable("id") String transactionId) {
        LOGGER.debug("Enter in sse-caregiver");
    	return transactionService.subscribeTansactionSSECaregiver(transactionId);
    }


    // --------- TEST ----------


    @Autowired
    private MessageProcessor processor;

    private ReplayProcessor<ServerSentEvent<String>> replayProcessor = ReplayProcessor.<ServerSentEvent<String>>create(100);

    @GetMapping(path = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> receive() {
        // Some FluxSink documentation and code samples:
        // - https://projectreactor.io/docs/core/release/reference/#producing.create
        // - https://www.baeldung.com/reactor-core
        // - https://www.e4developer.com/2018/04/14/webflux-and-servicing-client-requests-how-does-it-work/

        return Flux.create(sink -> processor.register(sink::next));
    }

    @PostMapping(path = "/test")
    public String send(@RequestBody String message) {
        LOGGER.info("Received '{}'", message);
        processor.process(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " " + message);
        return "Done";
    }
}

@Service
class MessageProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageProcessor.class);

    private List<Consumer<String>> listeners = new CopyOnWriteArrayList<>();

    public void register(Consumer<String> listener) {
        listeners.add(listener);
        LOGGER.info("Added a listener, for a total of {} listener{}", listeners.size(), listeners.size() > 1 ? "s" : "");
    }

    // TODO FBE implement unregister

    public void process(String message) {
        listeners.forEach(c -> c.accept(message));
    }
}
