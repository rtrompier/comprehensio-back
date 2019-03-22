package ch.hcuge.comprehensio.service;

import java.time.Duration;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.hcuge.comprehensio.entity.Lang;
import ch.hcuge.comprehensio.entity.State;
import ch.hcuge.comprehensio.entity.Transaction;
import ch.hcuge.comprehensio.repository.LangRepository;
import ch.hcuge.comprehensio.repository.TransactionRepository;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private LangRepository langRepository;

	@Transactional(readOnly = true)
	public Iterable<Transaction> getTransactions() {
		return this.transactionRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Optional<Transaction> getTransaction(String id) {
		 return this.transactionRepository.findById(id);
	}

	@Transactional
	public Transaction saveTransaction(Transaction tr) {
		Lang fromLang = this.langRepository.findById(tr.getFromLang().getId().toLowerCase()).get();
		tr.setFromLang(fromLang);
		Lang toLang = this.langRepository.findById(tr.getToLang().getId().toLowerCase()).get();
		tr.setToLang(toLang);
		return this.transactionRepository.save(tr);
	}

	@Transactional
	public Transaction updateTransaction(Transaction tr) {

		Optional<Transaction> current = this.transactionRepository.findById(tr.getId());
		if (current.isPresent()) {
			Transaction tr2 = current.get();
			if (tr.getFromLang() != null) {
				Lang fromLang = this.langRepository.findById(tr.getFromLang().getId().toLowerCase()).get();
				tr.setFromLang(fromLang);
			} else if (tr2.getFromLang() != null) {
				tr.setFromLang(tr2.getFromLang());
			}
			if (tr.getToLang() != null) {
				Lang toLang = this.langRepository.findById(tr.getToLang().getId().toLowerCase()).get();
				tr.setToLang(toLang);
			} else if (tr2.getToLang() != null) {
				tr.setToLang(tr2.getToLang());
			}
			
			tr.setStartDate(tr2.getStartDate());
			if(tr.getState() == null) {
				tr.setState(tr2.getState());
			}
			if(tr.getCaller() == null) {
				tr.setCaller(tr2.getCaller());
			}
			if(tr.getReceiver() == null) {
				tr.setReceiver(tr2.getReceiver());
			}
		}

		return this.transactionRepository.save(tr);
	}

	@Transactional
	public Transaction pendingTransaction(@NotEmpty String id, Date startDate) {
		Transaction t = transactionRepository.findById(id).get();
		t.setStartDate(startDate);
		t.setState(State.PENDING);
		return transactionRepository.save(t);
	}

	@Transactional
	public Transaction endTransaction(@NotEmpty String id, Date endDate) {
		Transaction t = transactionRepository.findById(id).get();
		t.setEndDate(endDate);
		t.setState(State.CLOSE);
		return transactionRepository.save(t);
	}

	public Flux<Transaction> getTransactions(String userId) {
		Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
		Flux<Transaction> stockTransactionFlux = Flux
				.fromStream(Stream.generate(() -> this.transactionRepository.findById(userId).get()));
		return Flux.zip(interval, stockTransactionFlux).map(Tuple2::getT2);
	}

}
