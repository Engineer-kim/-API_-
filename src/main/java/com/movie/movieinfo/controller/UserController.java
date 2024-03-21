package com.movie.movieinfo.controller;

import com.movie.movieinfo.dto.user.JoinRequestDto;
import com.movie.movieinfo.exception.UserAlreadyExistsException;
import com.movie.movieinfo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
