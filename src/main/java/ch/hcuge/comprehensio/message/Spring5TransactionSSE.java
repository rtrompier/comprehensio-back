package ch.hcuge.comprehensio.message;

import org.springframework.http.codec.ServerSentEvent;

import ch.hcuge.comprehensio.entity.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ReplayProcessor;

public class Spring5TransactionSSE implements TransactionMessageListener {

	private ReplayProcessor<ServerSentEvent<Transaction>> replayProcessor;

	public Spring5TransactionSSE() {
		this.replayProcessor = ReplayProcessor.<ServerSentEvent<Transaction>>create(100);
	}

	@Override
	public void onPostMessage(Transaction msg) {
		ServerSentEvent<Transaction> event = ServerSentEvent.builder(msg).event("message").id(msg.getId()).data(msg).build();
		replayProcessor.onNext(event);
	}

	public Flux<ServerSentEvent<Transaction>> subscribe(String lastEventId) {
		Integer lastId = (lastEventId != null) ? Integer.parseInt(lastEventId) : null;
		return replayProcessor
				.log("subscribe")
//				.filter(x -> lastId == null || Integer.parseInt(x.data().getId()) > lastId)
				;
	}
	

}
