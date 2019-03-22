package ch.hcuge.comprehensio.service;

import ch.hcuge.comprehensio.entity.Lang;
import ch.hcuge.comprehensio.entity.User;
import ch.hcuge.comprehensio.repository.RoleRepository;
import ch.hcuge.comprehensio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

	public Iterable<User> getUsers() {
		return this.userRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public User findUser(String userId) {
		return this.userRepository.findById(userId).get();
	}

	@Transactional
	public User save(User user) {
		return this.userRepository.save(user);
	}

	@Transactional
	public User addLang(String userId, Lang l) {
		User user = this.userRepository.findById(userId).get();
		user.getLangs().add(l);
		this.userRepository.save(user);
		return user;
	}
}
