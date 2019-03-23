package ch.hcuge.comprehensio.init;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.hcuge.comprehensio.entity.Lang;
import ch.hcuge.comprehensio.entity.Role;
import ch.hcuge.comprehensio.entity.User;
import ch.hcuge.comprehensio.repository.LangRepository;
import ch.hcuge.comprehensio.repository.RoleRepository;
import ch.hcuge.comprehensio.repository.UserRepository;

@Service
public class Initializer {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private LangRepository langRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @PostConstruct
    public void initRoles() {
        this.roleRepository.deleteAll();
        this.roleRepository.save(Role.builder().id("caregiver").label("Soignant").build());
        this.roleRepository.save(Role.builder().id("interpreter").label("Interprète").build());
    }

    @PostConstruct
    public void initLangs() {
        this.langRepository.deleteAll();
        this.langRepository.save(Lang.builder().id("fra").label("Français").build());
        this.langRepository.save(Lang.builder().id("eng").label("English").build());
    }
    
    
    @PostConstruct
    public void initUsers() {
        this.userRepository.deleteAll();
        this.userRepository.save(User.builder().id("user1").firstName("user_1").build());
        this.userRepository.save(User.builder().id("user2").firstName("user_2").build());
    }
    
    
}


