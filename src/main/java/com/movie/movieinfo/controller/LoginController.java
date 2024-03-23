package com.movie.movieinfo.controller;

import com.movie.movieinfo.entity.User;
import com.movie.movieinfo.service.user.UserService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/v1/login")
    public String login() {
        return "login.html";
    }

    @PostMapping("/loginPoc")
    public ModelAndView login(@RequestParam("userId") String userId,
                              @RequestParam("password") String password) {
        ModelAndView modelAndView = new ModelAndView();
        if (userService.authenticateUser(userId, password)) {
            modelAndView.setViewName("home.html");
        } else {
            modelAndView.setViewName("login.html");
            modelAndView.addObject("error", "Invalid username or password.");
        }
        return modelAndView;
    }

    @GetMapping("/loginSuccess")
    public String home() {
        return "home.html";
    }


    @
}
