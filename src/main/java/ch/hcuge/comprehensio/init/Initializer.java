package ch.hcuge.comprehensio.init;

import ch.hcuge.comprehensio.entity.Role;
import ch.hcuge.comprehensio.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class Initializer {

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void initRoles() {
        this.roleRepository.save(Role.builder().id("caregiver").label("Soignant").build());
        this.roleRepository.save(Role.builder().id("interpreter").label("Interprète").build());
    }
}
