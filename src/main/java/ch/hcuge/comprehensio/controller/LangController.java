package ch.hcuge.comprehensio.controller;

import ch.hcuge.comprehensio.entity.Lang;
import ch.hcuge.comprehensio.service.LangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/langs")
public class LangController {

    @Autowired
    private LangService langService;

    @GetMapping
    public ResponseEntity<Iterable<Lang>> getLangs() {
        return ResponseEntity.ok(this.langService.getLangs());
    }

}
