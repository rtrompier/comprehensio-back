package ch.hcuge.comprehensio.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.hcuge.comprehensio.entity.Transaction;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, String> {

}
