package ch.hcuge.comprehensio.repository;

import ch.hcuge.comprehensio.entity.Lang;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LangRepository extends CrudRepository<Lang, String> {

}
