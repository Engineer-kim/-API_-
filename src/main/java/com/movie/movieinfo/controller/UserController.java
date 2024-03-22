package com.movie.movieinfo.controller;

import com.movie.movieinfo.dto.user.JoinRequestDto;
import com.movie.movieinfo.exception.UserAlreadyExistsException;
import com.movie.movieinfo.response.CustomResponse;
import com.movie.movieinfo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**회원 가입 API*/
    @PostMapping("/v1/register")
    public ResponseEntity<?> registerUserAccount(@RequestBody JoinRequestDto joinRequestDto) {
        ResponseEntity<?> response = userService.registerNewUserAccount(joinRequestDto);
        return response;
    }

    /**아이디 중복체크 API */
    @GetMapping("/v1/duplicateCheckId")
    public ResponseEntity<CustomResponse> duplicateCheckId(@RequestParam String userId) {
        CustomResponse response = userService.checkIfUserIdExists(userId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    /**아이디 찿기 기능 API*/
    @GetMapping("/v1/findUserId")
    public ResponseEntity<?> findUserIdsByEmail(@RequestParam String email) {
        List<String> userIds = userService.findUserByEmail(email);
        if (userIds.isEmpty()) {
            return ResponseEntity.ok("가입된 계정정보가 없습니다");
        }
        return ResponseEntity.ok().body(userIds);
    }

}
