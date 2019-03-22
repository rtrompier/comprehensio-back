package ch.hcuge.comprehensio.controller;

import ch.hcuge.comprehensio.entity.User;
import ch.hcuge.comprehensio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

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
    public ResponseEntity<User> save(@RequestBody @NotNull User user) {
        return ResponseEntity.ok(this.userService.save(user));
    }

}
