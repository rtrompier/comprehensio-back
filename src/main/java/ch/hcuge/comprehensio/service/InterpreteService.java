package ch.hcuge.comprehensio.service;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.hcuge.comprehensio.entity.User;
import ch.hcuge.comprehensio.repository.LangRepository;
import ch.hcuge.comprehensio.repository.UserRepository;

@Service
public class InterpreteService {

	@Autowired
	public UserRepository userRepository;
	
	@Autowired
	public LangRepository langRepository;
	
	@Transactional
	public List<User> findUserByLang(@NotBlank String langFrom, @NotBlank String langTo) {
		return userRepository.findLangs(langFrom, langTo);
	}

}
