package ch.hcuge.comprehensio.message;

import ch.hcuge.comprehensio.entity.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public abstract class TransactionProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionProcessor.class);

    private List<Consumer<Transaction>> listeners = new CopyOnWriteArrayList<>();

    public void register(Consumer<Transaction> listener) {
        listeners.add(listener);
        LOGGER.info("Added a listener, for a total of {} listener{}", listeners.size(), listeners.size() > 1 ? "s" : "");
    }

    // TODO FBE implement unregister

    public void process(Transaction transaction) {
        listeners.forEach(c -> c.accept(transaction));
    }
}
