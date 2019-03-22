package ch.hcuge.comprehensio.controller;

import ch.hcuge.comprehensio.entity.User;
import ch.hcuge.comprehensio.service.LoginService;
import ch.hcuge.comprehensio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping
    public ResponseEntity<User> login(Principal principal) {
        return ResponseEntity.ok(this.loginService.login(principal));
    }

}
