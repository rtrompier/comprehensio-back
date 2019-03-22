package ch.hcuge.comprehensio.controller;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.hcuge.comprehensio.service.InterpreteService;

@RestController
@RequestMapping("/interpretes")
public class InterpreteController {

    @Autowired
    private InterpreteService interpreteService;

    @GetMapping
    public ResponseEntity<?> getInterpretes(@RequestParam("langFrom")@NotBlank String langFrom, @RequestParam("langFrom")@NotBlank String langTo) {
    	List result = interpreteService.findUserByLang(langFrom, langTo);
    	return ResponseEntity.ok(result);
    }

}
