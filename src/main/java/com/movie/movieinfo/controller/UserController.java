package com.movie.movieinfo.controller;

import com.movie.movieinfo.dto.user.JoinRequestDto;
import com.movie.movieinfo.exception.UserAlreadyExistsException;
import com.movie.movieinfo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUserAccount(@RequestBody JoinRequestDto joinRequestDto) {
        try {
            userService.registerNewUserAccount(joinRequestDto);
            return ResponseEntity.ok().body("User registered successfully");
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "/";
    }
}
