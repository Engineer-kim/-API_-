package com.movie.movieinfo.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        //하위 엔드포인트는 미인증이여도 접근가능하게끔
                        .requestMatchers("/api/user/v1/findUserId",
                                "/api/user/v1/duplicateCheckId", "/api/user/v1/register" ,"/api/auth/v1/login",
                                "/api/auth/loginPoc",
                                "/movieInfo/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/api/auth/v1/login")
                        .loginProcessingUrl("/api/auth/loginProc")
                        .usernameParameter("userId")
                        .passwordParameter("password")
                        .successHandler(
                                new AuthenticationSuccessHandler() {
                                    @Override
                                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                                        System.out.println("authentication::::::: " + authentication.getName());
                                        response.sendRedirect("/api/auth/loginSuccess");
                                    }
                                })
                        .failureHandler(
                                new AuthenticationFailureHandler() {
                                    @Override
                                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                                        System.out.println("exception::::::::::::: " + exception.getMessage());
                                        response.sendRedirect("/api/auth/login");
                                    }
                                })
                        .permitAll()

                )
                .rememberMe(Customizer.withDefaults())
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .logoutSuccessUrl("/api/auth/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
