package ch.hcuge.comprehensio.repository;

import ch.hcuge.comprehensio.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

}
