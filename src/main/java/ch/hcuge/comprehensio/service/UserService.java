package ch.hcuge.comprehensio.service;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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

	public User saveOrUpdateUser(Principal principal) {
		JwtAuthenticationToken auth = ((JwtAuthenticationToken) principal);
		User user = new User();

		if (auth.getTokenAttributes().containsKey("sub")) {
			user.setId((String) auth.getTokenAttributes().get("sub"));
		}
		if (auth.getTokenAttributes().containsKey("family_name")) {
			user.setLastName((String) auth.getTokenAttributes().get("family_name"));
		}
		if (auth.getTokenAttributes().containsKey("given_name")) {
			user.setFirstName((String) auth.getTokenAttributes().get("given_name"));
		}
		if (auth.getTokenAttributes().containsKey("email")) {
			user.setEmail((String) auth.getTokenAttributes().get("email"));
		}

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
