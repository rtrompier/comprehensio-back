package ch.hcuge.comprehensio.message;

import org.springframework.http.codec.ServerSentEvent;

import ch.hcuge.comprehensio.entity.State;
import ch.hcuge.comprehensio.entity.Transaction;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ReplayProcessor;

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

	public Flux<ServerSentEvent<Transaction>> subscribe(String lastEventId) {
		return replayProcessor
				.log("subscribe")
				.filter(x -> x.data().getState() == State.PENDING)
				;
	}
	
	
	public Flux<ServerSentEvent<Transaction>> subscribe2(String transactionId) {
		return replayProcessor
				.log("subscribe2")
				.filter(x -> transactionId != null && transactionId.equals(x.data().getId()))
				;
	}

}
