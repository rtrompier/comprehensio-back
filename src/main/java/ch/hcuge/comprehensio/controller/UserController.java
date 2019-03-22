package ch.hcuge.comprehensio.controller;

import ch.hcuge.comprehensio.entity.User;
import ch.hcuge.comprehensio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.security.Principal;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Iterable<User>> getUsers() {
        return ResponseEntity.ok(this.userService.getUsers());
    }

    @PostMapping
    public ResponseEntity<User> saveOrUpdateUser(Principal principal) {
        return ResponseEntity.ok(this.userService.saveOrUpdateUser(principal));
    }

}
