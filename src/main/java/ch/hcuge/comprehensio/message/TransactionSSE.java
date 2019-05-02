package ch.hcuge.comprehensio.message;

import org.springframework.http.codec.ServerSentEvent;

import ch.hcuge.comprehensio.entity.State;
import ch.hcuge.comprehensio.entity.Transaction;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;

public class TransactionSSE implements TransactionMessageListener {

//	private ReplayProcessor<ServerSentEvent<Transaction>> replayProcessor;
	private EmitterProcessor<ServerSentEvent<Transaction>> replayProcessor;

	public TransactionSSE() {
//		this.replayProcessor = ReplayProcessor.<ServerSentEvent<Transaction>>create(100);
		this.replayProcessor = EmitterProcessor.<ServerSentEvent<Transaction>>create(100);
	}

	@Override
	public void onPostMessage(Transaction msg) {
		ServerSentEvent<Transaction> event = ServerSentEvent.builder(msg).event("message").id(msg.getId()).data(msg).build();
		replayProcessor.onNext(event);
	}

	public Flux<ServerSentEvent<Transaction>> subscribe() {
		return replayProcessor
				.log("Interpreter")
				.doOnError(e-> System.err.println("Erreur Interpreter :: " + e.getMessage()))
				.filter(x -> x.data().getState() != State.INPROGRESS )
				;
	}
	
	
	public Flux<ServerSentEvent<Transaction>> subscribe2(String transactionId) {
		return replayProcessor
				.log("Soignants")
				.doOnError(e-> System.err.println("Erreur Soignants " + transactionId + " :: " + e.getMessage()))
				.doOnEach(e -> System.out.println("error soignant " + transactionId))
				.filter(x -> transactionId != null && transactionId.equals(x.data().getId()))
				;
	}

}
