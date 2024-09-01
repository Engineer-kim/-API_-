package com.movie.movieinfo.config;

import com.movie.movieinfo.service.user.OAuth2Service;
import com.movie.movieinfo.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpSession;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;
    private final SHA256PasswordEncoder sha256PasswordEncoder;

    public SecurityConfig(UserService userService, SHA256PasswordEncoder sha256PasswordEncoder) {
        this.userService = userService;
        this.sha256PasswordEncoder = sha256PasswordEncoder;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public OAuth2Service customOAuth2Service() {
        return new OAuth2Service();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1)
                        .expiredUrl("/api/auth/commonLogin")
        );
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/api/user/v1/findUserId",
                                "/api/user/v1/duplicateCheckId",
                                "/api/user/v1/register",
                                "/api/auth/commonLogin",
                                "/movieInfo/**",
                                "/movieInfoMain/**",
                                "/userManagement/**",
                                "/api/user/v1/resetPassword/request",
                                "/api/user/v1/resetPassword",
                                "/movieReview/v1/getMovieAllReview"
                        )
                        .permitAll()
                        .requestMatchers(request ->
                                "XMLHttpRequest".equals(request.getHeader("X-Requested-With")))
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/api/auth/commonLogin")
                        .loginProcessingUrl("/api/auth/loginProc")
                        .usernameParameter("userId")
                        .passwordParameter("password")
                        .successHandler(new AuthenticationSuccessHandler() {
                            @Override
                            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                                System.out.println("authentication::::::: " + authentication.getName());
                                HttpSession session = request.getSession(true);
                                session.setAttribute("userName", authentication.getName());
                                session.setAttribute("sessionExists", true);
                                response.sendRedirect("/api/auth/loginSuccess");
                            }
                        })
                        .failureUrl("/api/auth/commonLogin?error")
                        .permitAll()
                )
                .oauth2Login((oauth2) -> oauth2
                        .loginPage("/api/auth/commonLogin")
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint.userService(customOAuth2Service()))
                        .successHandler(new SimpleUrlAuthenticationSuccessHandler("/api/auth/loginSuccess"))
                )
                .rememberMe(Customizer.withDefaults())
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .logoutSuccessUrl("/api/auth/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID", "remember-me")
                        .permitAll()
                )
                .rememberMe(Customizer.withDefaults())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1)
                        .expiredUrl("/api/auth/commonLogin")
                )
                .authenticationProvider(authenticationProvider());
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(sha256PasswordEncoder);
        return authProvider;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> {
            web
                    .ignoring()
                    .requestMatchers(
                            PathRequest.toStaticResources().atCommonLocations()
                    );
        };
    }
}
