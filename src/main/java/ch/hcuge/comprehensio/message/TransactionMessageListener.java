package ch.hcuge.comprehensio.message;

import ch.hcuge.comprehensio.entity.Transaction;

public interface TransactionMessageListener {

	public void onPostMessage(Transaction msg);
}
