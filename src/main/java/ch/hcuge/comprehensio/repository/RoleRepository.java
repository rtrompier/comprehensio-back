package ch.hcuge.comprehensio.repository;

import ch.hcuge.comprehensio.entity.Role;
import ch.hcuge.comprehensio.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, String> {

}
