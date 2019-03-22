package ch.hcuge.comprehensio.service;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.hcuge.comprehensio.entity.Transaction;
import ch.hcuge.comprehensio.repository.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Transactional(readOnly = true)
	public Iterable<Transaction> getTransactions() {
		return this.transactionRepository.findAll();
	}

	@Transactional
	public Transaction saveTransaction(Transaction tr) {
		return this.transactionRepository.save(tr);
	}

	@Transactional
	public Transaction pendingTransaction(@NotEmpty String id, Date startDate) {
		Transaction t = transactionRepository.findById(id).get();
//		t.setStartDate(startDate);
//		t.setState(State.PENDING);
		return transactionRepository.save(t);
	}

	@Transactional
	public Transaction endTransaction(@NotEmpty String id, Date endDate) {
		Transaction t = transactionRepository.findById(id).get();
//		t.setEndDate(endDate);
//		t.setState(State.CLOSE);
		return transactionRepository.save(t);
	}

}
