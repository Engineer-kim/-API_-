package com.movie.movieinfo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginTestCode {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;


    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity()) // 스프링 시큐리티 적용
                .build();
    }

    @Test
    public void loginWithValidUserTest() throws Exception {
        mockMvc.perform(formLogin("/login").user("user").password("password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(authenticated().withUsername("user"));
    }
    @Test
    public void loginWithInvalidUserTest() throws Exception {
        mockMvc.perform(formLogin("/login").user("invalidUser").password("invalidPassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(unauthenticated());
    }
}
