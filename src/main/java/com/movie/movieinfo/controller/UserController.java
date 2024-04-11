package com.movie.movieinfo.controller;

import com.movie.movieinfo.dto.password.PasswordResetDto;
import com.movie.movieinfo.dto.user.DeleteRequestDto;
import com.movie.movieinfo.dto.user.JoinRequestDto;
import com.movie.movieinfo.exception.UserAlreadyExistsException;
import com.movie.movieinfo.exception.UserEmailNotFoundException;
import com.movie.movieinfo.response.CustomResponse;
import com.movie.movieinfo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 회원 가입 API
     */
    @PostMapping("/v1/register")
    public ResponseEntity<?> registerUserAccount(@RequestBody JoinRequestDto joinRequestDto) {
        ResponseEntity<?> response = userService.registerNewUserAccount(joinRequestDto);
        return response;
    }

    /**
     * 아이디 중복체크 API
     */
    @GetMapping("/v1/duplicateCheckId")
    public ResponseEntity<CustomResponse> duplicateCheckId(@RequestParam String userId) {
        CustomResponse response = userService.checkIfUserIdExists(userId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    /**
     * 아이디 찿기 기능 API
     */
    @GetMapping("/v1/findUserId")
    public ResponseEntity<CustomResponse> getUserIdsByEmail(@RequestParam String email) {
        CustomResponse response = userService.findUserIdsByEmailResponse(email);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @PostMapping("/v1/deleteUser")
    public  ResponseEntity<?> deleteUserAccount(@RequestBody DeleteRequestDto deleteRequestDto){
        boolean isDeleted = userService.deleteUserAccount(deleteRequestDto.getUserId());
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body("회원 탈퇴 처리에 완료되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("회원 탈퇴 처리에 실패했습니다");
        }
    }



    /**비밀번호 초기화 API*/

    /**비밀번호 초기화를 위해 회원가입시 입력했던 이메일 입력*/
    /**
     * resetPasswordRequest.html
     * 에서 입력한 이메일 바탕으로 재설정처리
     */
    @GetMapping("/v1/resetPasswordPageMove")
    public String showResetPasswordForm() {
        return "resetPasswordRequest.html";
    }

    /**
     * 비밀번호 재설정 요청 처리
     */
    @PostMapping("/v1/resetPassword/request")
    public ResponseEntity<CustomResponse> sendResetPasswordLink(@RequestBody String userEmail) {
        try {
            System.out.println("Controller :::::::::::::::::::::::::::" +userEmail);
            userService.sendPasswordResetLink(userEmail);
            return ResponseEntity.ok(new CustomResponse(HttpStatus.OK.value(), "비밀번호 재설정 링크가 입력하신 이메일로 성공적으로 전송되었습니다."));
        } catch (UserEmailNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CustomResponse(HttpStatus.NOT_FOUND.value(), "해당 이메일로 가입된 정보가 없습니다"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "비밀번호 재설정 링크 전송 중 에러가 발생했습니다."));
        }
    }

    /**
     * 비밀번호 재설정 처리
     */
    @PostMapping("/v1/resetPassword")
    public ResponseEntity<CustomResponse> resetPassword(@RequestBody PasswordResetDto passwordResetDto) {
        CustomResponse response = userService.resetPassword(passwordResetDto.getToken(), passwordResetDto.getNewPassword());
        HttpStatus status = HttpStatus.resolve(response.getStatusCode());
        if (status == null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR; // 유효하지 않은 상태 코드 처리
        }
        return ResponseEntity.status(status).body(response);
    }

}
