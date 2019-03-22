package ch.hcuge.comprehensio.service;

import ch.hcuge.comprehensio.entity.Lang;
import ch.hcuge.comprehensio.repository.LangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LangService {

    @Autowired
    private LangRepository langRepository;

    public Iterable<Lang> getLangs() {
        return this.langRepository.findAll();
    }
}
