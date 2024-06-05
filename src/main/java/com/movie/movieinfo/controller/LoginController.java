package com.movie.movieinfo.controller;

import com.movie.movieinfo.entity.User;
import com.movie.movieinfo.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @GetMapping("/commonLogin")
    public String login() {
        return "login.html";
    }

    @PostMapping("/loginPoc")
    public ModelAndView login(@RequestParam("userId") String userId,
                              @RequestParam("password") String password, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        if (userService.authenticateUser(userId, password)) {
            HttpSession session = request.getSession(); // 세션 생성 또는 기존 세션 가져오기
            session.setAttribute("userName", userId); // 로그인 성공 시 세션에 userName 설정
            modelAndView.setViewName("movieInfoMain.html");
        } else {
            modelAndView.setViewName("login.html");
            modelAndView.addObject("error", "Invalid username or password.");
        }
        return modelAndView;
    }

    @GetMapping("/loginSuccess")
    public String home() {
        return "movieInfoMain.html";
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        // 사용자 세션을 무효화
        SecurityContextHolder.clearContext();
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok().body("로그아웃 성공");
    }
}
