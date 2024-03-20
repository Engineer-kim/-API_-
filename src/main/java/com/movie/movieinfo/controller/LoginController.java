package com.movie.movieinfo.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.reflect.Member;

@Controller
public class LoginController {
    @PostMapping("/login") // 세션에 담아주기
    public String loginv2(@ModelAttribute LoginForm form, Model model,
                          RedirectAttributes redirectAttributes , HttpServletRequest request) {
        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

        System.out.println(loginMember);

        if( loginMember == null) {
            // 로그인실패
            model.addAttribute("msg", "로그인실패");
            return "login/loginForm";
        }
        // 로그인 성공
        HttpSession session = request.getSession();
        //세션에 로그인 회원정보 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        redirectAttributes.addFlashAttribute("msg","로그인 성공");
        return "redirect:/";
    }


    @PostMapping("/logout")
    public String logoutv2(HttpServletRequest request) {
        //세션을 삭제
        HttpSession session = request.getSession(false);
        // session이 null이 아니라는건 기존에 세션이 존재했었다는 뜻이므로
        // 세션이 null이 아니라면 session.invalidate()로 세션 삭제해주기.
        if(session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
