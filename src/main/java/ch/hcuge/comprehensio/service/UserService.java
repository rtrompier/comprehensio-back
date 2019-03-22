package ch.hcuge.comprehensio.service;

import ch.hcuge.comprehensio.entity.User;
import ch.hcuge.comprehensio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Iterable<User> getUsers() {
        return this.userRepository.findAll();
    }

    public User saveOrUpdateUser(Principal principal) {
        JwtAuthenticationToken auth = ((JwtAuthenticationToken) principal);
        User user = new User();

        if(auth.getTokenAttributes().containsKey("sub")) {
            user.setId((String) auth.getTokenAttributes().get("sub"));
        }
        if(auth.getTokenAttributes().containsKey("family_name")) {
            user.setLastName((String) auth.getTokenAttributes().get("family_name"));
        }
        if(auth.getTokenAttributes().containsKey("given_name")) {
            user.setFirstName((String) auth.getTokenAttributes().get("given_name"));
        }
        if(auth.getTokenAttributes().containsKey("email")) {
            user.setEmail((String) auth.getTokenAttributes().get("email"));
        }

        return this.userRepository.save(user);
    }
}
