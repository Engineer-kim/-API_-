package com.movie.movieinfo.service.user;

import com.movie.movieinfo.Repository.UserRepository;
import com.movie.movieinfo.dto.user.JoinRequestDto;
import com.movie.movieinfo.entity.User;
import com.movie.movieinfo.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService{


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User registerNewUserAccount(JoinRequestDto joinRequestDto) throws UserAlreadyExistsException {
        if (userRepository.findByUsername(joinRequestDto.getUserName()).isPresent()) {
            throw new UserAlreadyExistsException("There is an account with that email address: " + joinRequestDto.getUserName());
        }
        User user = User.builder()
               .dbSts('A')
               .userName(joinRequestDto.getUserName())
               .id(joinRequestDto.getUserId())
               .userEmaiil(joinRequestDto.getUserEmail())
               .password(passwordEncoder.encode(joinRequestDto.getUserPassword()))
               .build();
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));


        return new org.springframework.security.core.userdetails.User
                (user.getUserName(), user.getPassword(), Collections.emptyList());
    }

}
