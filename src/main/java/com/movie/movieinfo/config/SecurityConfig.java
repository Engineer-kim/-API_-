package com.movie.movieinfo.config;

import com.movie.movieinfo.service.user.OAuth2Service;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
    public OAuth2Service customOAuth2Service(){
        return new OAuth2Service();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //   /api/auth/commonLogin 는 일반 로그인 
                //  /api/auth/login 구글 로그인
                .authorizeHttpRequests(authorize -> authorize
                        //하위 엔드포인트는 미인증이여도 접근가능하게끔
                        .requestMatchers("/api/user/v1/findUserId",
                                "/api/user/v1/duplicateCheckId", "/api/user/v1/register" ,"/api/auth/commonLogin"
                                ,"/api/auth/login",
                                "/api/auth/loginPoc",
                                "/movieInfo/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/api/auth/commonLogin")
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
                                        response.sendRedirect("/api/auth/commonLogin");
                                    }
                                })
                        .permitAll()

                )
                .oauth2Login((oauth2) -> oauth2
                        .loginPage("/api/auth/login")
                        .defaultSuccessUrl("/api/auth/loginSuccess")
                        .failureUrl("/api/auth/login")
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                                .userService(customOAuth2Service()))
                )
                .rememberMe(Customizer.withDefaults())
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .logoutSuccessUrl("/api/auth/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID", "remember-me")
                        .permitAll()
                );
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
