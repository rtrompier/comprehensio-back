package ch.hcuge.comprehensio.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.hcuge.comprehensio.entity.Lang;
import ch.hcuge.comprehensio.repository.LangRepository;

@Service
public class LangService {

    @Autowired
    private LangRepository langRepository;

    @Transactional
    public Iterable<Lang> getLangs() {
        return this.langRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Lang findLang(String id) {
        return this.langRepository.findById(id).get();
    }
}
