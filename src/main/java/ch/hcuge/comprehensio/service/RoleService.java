package ch.hcuge.comprehensio.service;

import ch.hcuge.comprehensio.entity.Role;
import ch.hcuge.comprehensio.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Iterable<Role> getRoles() {
        return this.roleRepository.findAll();
    }

    public Optional<Role> getRolebyId(String id) {
        return this.roleRepository.findById(id);
    }

}
