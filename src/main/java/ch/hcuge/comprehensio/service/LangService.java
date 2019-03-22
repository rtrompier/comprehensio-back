package ch.hcuge.comprehensio.service;

import ch.hcuge.comprehensio.entity.Lang;
import ch.hcuge.comprehensio.repository.LangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class LangService {

    @Autowired
    private LangRepository langRepository;

    @Transactional
    public Iterable<Lang> getLangs() {
        return this.langRepository.findAll();
    }
}
