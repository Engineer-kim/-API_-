package com.movie.movieinfo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.movie.movieinfo.Repository.UserRepository;
import com.movie.movieinfo.dto.user.JoinRequestDto;
import com.movie.movieinfo.entity.User;
import com.movie.movieinfo.exception.UserAlreadyExistsException;
import com.movie.movieinfo.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

public class JoinTestCode {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenRegisterNewUser_thenSucceed() throws UserAlreadyExistsException {

        JoinRequestDto joinRequestDto = new JoinRequestDto("name12313", "1232132password", "userId0103", "userEmail@naver.com", LocalDateTime.now());

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        User mockUser = User.builder()
                .dbSts("A")
                .userName(joinRequestDto.getUserName())
                .id(joinRequestDto.getUserId())
                .userEmail(joinRequestDto.getUserEmail())
                .password(passwordEncoder.encode(joinRequestDto.getUserPassword()))
                .signDate(LocalDateTime.now())
                .build();

        when(userRepository.findByUserId(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        User result = userService.registerNewUserAccount(joinRequestDto);

        assertNotNull(result);
        assertEquals(joinRequestDto.getUserName(), result.getUserName());
        assertEquals(joinRequestDto.getUserId(), result.getId());
        assertEquals(joinRequestDto.getUserEmail(), result.getUserEmail());
        assertEquals("encodedPassword", result.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void whenRegisterExistingUser_thenThrowException() {

        JoinRequestDto joinRequestDto = new JoinRequestDto("name12313", "1232132112323password", "userId0103", "userEmail@naver.com", LocalDateTime.now());
        when(userRepository.findByUserId(anyString())).thenReturn(Optional.of(new User()));

        assertThrows(UserAlreadyExistsException.class, () -> {
            userService.registerNewUserAccount(joinRequestDto);
        });
    }
}
