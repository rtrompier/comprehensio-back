package ch.hcuge.comprehensio.repository;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ch.hcuge.comprehensio.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	@Query(value = "SELECT * FROM User u INNER JOIN user_langs ul on ul.user_id=u.id where ul.lang_id=?1", nativeQuery=true)
	List<User> findLangs(@NotBlank String langFrom, @NotBlank String langTo);
	
}
