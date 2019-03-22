package ch.hcuge.comprehensio.service;

import ch.hcuge.comprehensio.entity.Role;
import ch.hcuge.comprehensio.entity.User;
import ch.hcuge.comprehensio.repository.RoleRepository;
import ch.hcuge.comprehensio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public Iterable<User> getUsers() {
        return this.userRepository.findAll();
    }

    @Transactional
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


        if(auth.getAuthorities() != null) {
            List<String> ids = auth.getAuthorities().stream()
                    .map(a -> a.getAuthority())
                    .collect(Collectors.toList());
            Set<Role> roles = this.roleRepository.findByIds(ids);
            user.setRoles(roles);
        }

        return this.userRepository.save(user);
    }
}
