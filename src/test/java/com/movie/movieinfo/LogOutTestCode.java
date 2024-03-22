//package com.movie.movieinfo;
//
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest
//public class LogOutTestCode {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    @WithMockUser
//    public void logoutSuccessTest() throws Exception {
//        mockMvc.perform(logout("/logout"))
//                .andExpect(status().isFound()) // 로그아웃 성공시 리다이렉트 되므로 302 상태 코드 확인
//                .andExpect(redirectedUrl("/")); // 로그아웃 성공시 리다이렉트 URL 확인
//    }
//}
