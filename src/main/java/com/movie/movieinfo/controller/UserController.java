package com.movie.movieinfo.controller;

import com.movie.movieinfo.dto.user.JoinRequestDto;
import com.movie.movieinfo.exception.UserAlreadyExistsException;
import com.movie.movieinfo.service.user.UserService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<String> registerUserAccount(@RequestBody JoinRequestDto joinRequestDto) {
        //서버 측 방어코드
        // (원래는 클라이언트에서 회원가입시 중복체크 API호출 하여 중복체크를 강제화하여 중복되지 않은 아이디로 하여금 회원가입 되게끔 해야함)
        ResponseEntity<String> userIdExistsResponse = userService.checkIfUserIdExists(joinRequestDto.getUserId());
        if (userIdExistsResponse.getStatusCode().is2xxSuccessful()) {
            return userIdExistsResponse;
        }
        // 기존 회원 가입 된 ID가 아니라면 회원가입
        userService.registerNewUserAccount(joinRequestDto);
        return ResponseEntity.ok().body("회원가입 성공");
    }


    /**아이디 중복체크 API */
    @GetMapping("/v1/duplicateCheckId")
    public ResponseEntity<String> duplicateCheckId(@RequestParam String userId) {
        try {
            userService.checkIfUserIdExists(userId);
            return ResponseEntity.ok().body("사용 가능한 아이디입니다.");
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body("중복된 아이디로 사용이 불가합니다");
        }
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
