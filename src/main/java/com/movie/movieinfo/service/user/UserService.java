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


import java.time.LocalDateTime;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService{


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User registerNewUserAccount(JoinRequestDto joinRequestDto) throws UserAlreadyExistsException {
        checkIfUserIdExists(joinRequestDto.getUserId());
        User user = User.builder()
                .dbSts("A")
                .userName(joinRequestDto.getUserName())
                .id(joinRequestDto.getUserId())
                .userEmail(joinRequestDto.getUserEmail())
                .password(passwordEncoder.encode(joinRequestDto.getUserPassword()))
                .signDate(LocalDateTime.now())
                .build();
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("해당 사용자의 이름으로 가입된 계정이 있습니다: " + userName));
        return new org.springframework.security.core.userdetails.User
                (user.getUserName(), user.getId(), Collections.emptyList());
    }

    public void checkIfUserIdExists(String userId) throws UserAlreadyExistsException {
        if (userRepository.findByUserId(userId).isPresent()) {
            throw new UserAlreadyExistsException("해당 아이디로 가입된 계정이 있습니다: " + userId);
        }
    }



}
