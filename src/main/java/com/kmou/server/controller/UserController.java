package com.kmou.server.controller;

import com.kmou.server.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/username")
    public ResponseEntity<Boolean> checkUsername(@RequestParam String username) {

        boolean isAvailable = userService.isUsernameAvailable(username);

        return ResponseEntity.ok(isAvailable);
    }

}
