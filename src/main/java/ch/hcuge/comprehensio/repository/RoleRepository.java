package ch.hcuge.comprehensio.repository;

import ch.hcuge.comprehensio.entity.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RoleRepository extends CrudRepository<Role, String> {

    @Query( "select r from Role r where r.id in :ids" )
    Set<Role> findByIds(List<String> ids);

}
