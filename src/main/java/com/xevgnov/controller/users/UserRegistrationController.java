package com.xevgnov.controller.users;


import com.xevgnov.model.users.User;
import com.xevgnov.service.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRegistrationController {

    private final UserService userService;

    @Autowired
    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(@Valid @RequestBody User user) {
        User dbUser = userService.findUserByEmail(user.getEmail());
        if (dbUser != null) {
            return ResponseEntity.badRequest().body("The email [" + user.getEmail() + "] is already used! Please provide another email.");
        } else {
            userService.createUser(user);
            dbUser = userService.findUserByEmail(user.getEmail());
            return ResponseEntity.ok("User with ID [" + dbUser.getId() + "] has been created.");
        }
    }

}
