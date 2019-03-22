package ch.hcuge.comprehensio.service;

import ch.hcuge.comprehensio.entity.User;
import ch.hcuge.comprehensio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Iterable<User> getUsers() {
        return this.userRepository.findAll();
    }
}
