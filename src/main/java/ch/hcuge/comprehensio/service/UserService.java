package ch.hcuge.comprehensio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.hcuge.comprehensio.entity.Lang;
import ch.hcuge.comprehensio.entity.User;
import ch.hcuge.comprehensio.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

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

	public User getUserById(String userId) {
		return this.userRepository.findById(userId).get();
	}
}
