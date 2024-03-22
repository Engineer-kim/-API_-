//package com.movie.movieinfo;
//
//import org.apache.catalina.security.SecurityConfig;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest
//@Import(SecurityConfig.class)
//public class LoginTestCode {
//
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void shouldPermitAllForLoginPage() throws Exception {
//        mockMvc.perform(get("/login"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void shouldPermitAllForHomePage() throws Exception {
//        mockMvc.perform(get("/"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @WithMockUser // 가짜 인증된 사용자를 사용하여 인증된 요청을 시뮬레이션합니다.
//    public void shouldAllowAuthenticatedRequestToAnyPage() throws Exception {
//        mockMvc.perform(get("/anyPage"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void shouldRedirectToLoginForUnauthenticatedRequests() throws Exception {
//        mockMvc.perform(get("/anyPage"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("http://localhost/login"));
//    }
//
//    @Test
//    public void shouldLoginSuccess() throws Exception {
//        mockMvc.perform(formLogin("/login").user("user").password("password"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/"));
//    }
//
//    @Test
//    public void shouldLogoutSuccessfully() throws Exception {
//        mockMvc.perform(post("/logout")
//                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/"));
//    }
//}
