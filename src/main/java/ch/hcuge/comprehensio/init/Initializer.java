package ch.hcuge.comprehensio.init;

import ch.hcuge.comprehensio.entity.Lang;
import ch.hcuge.comprehensio.entity.Role;
import ch.hcuge.comprehensio.repository.LangRepository;
import ch.hcuge.comprehensio.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class Initializer {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private LangRepository langRepository;

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
}
