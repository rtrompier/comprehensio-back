package ch.hcuge.comprehensio.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.hcuge.comprehensio.entity.Lang;

@Repository
public interface LangRepository extends CrudRepository<Lang, String> {

	
}
