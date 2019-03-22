package ch.hcuge.comprehensio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.hcuge.comprehensio.entity.Lang;
import ch.hcuge.comprehensio.service.LangService;
import ch.hcuge.comprehensio.service.UserService;

@RestController
@RequestMapping("/admininit")
public class InitController {
	
    @Autowired
    private UserService userService;
    
    @Autowired
    private LangService langService;

	
	@GetMapping
	public void initUserLang() {
        Lang lang = this.langService.findLang("fra");
        this.userService.addLang("user1", lang);
    }
}
