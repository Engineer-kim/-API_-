package com.movie.movieinfo.service.user;

import com.movie.movieinfo.Repository.UserRepository;
import com.movie.movieinfo.dto.user.JoinRequestDto;
import com.movie.movieinfo.entity.User;
import com.movie.movieinfo.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService{


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public User registerNewUserAccount(JoinRequestDto joinRequestDto) throws UserAlreadyExistsException {
        User user = User.builder()
                .dbSts(joinRequestDto.getDbSts())
                .userName(joinRequestDto.getUserName())
                .id(joinRequestDto.getUserId())
                .userEmail(joinRequestDto.getUserEmail())
                .password(passwordEncoder.encode(joinRequestDto.getUserPassword()))
                .signDate(LocalDateTime.now())
                .build();
        return userRepository.save(user);
    }
    public ResponseEntity<String> checkIfUserIdExists(String userId) {
        if (userRepository.findById(userId).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("중복된 아이디로 해당 아이디로 가입 불가합니다 ");
        }
        return ResponseEntity.notFound().build();
    }

    /**이메일로 유저 아이디 찿기(단건 혹은 다건)*/
    public List<String> findUserByEmail(String email) {
        return userRepository.findByUserEmail(email)
                .stream()//[User{id='id1', email='email1@email.com'}, User{id='id2', email='email2@email.com'}]
                .map(User::getId)//User{id='id1', email='email1@email.com'}  , User{id='id2', email='email2@email.com'}
                .collect(Collectors.toList()); //("id1", "id2")
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("해당 사용자의 이름으로 가입된 계정이 있습니다: " + userName));
        return new org.springframework.security.core.userdetails.User
                (user.getUserName(), user.getId(), Collections.emptyList());
    }


}
