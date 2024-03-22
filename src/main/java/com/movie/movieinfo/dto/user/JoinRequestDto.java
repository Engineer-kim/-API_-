package com.movie.movieinfo.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JoinRequestDto {
    private String userName;
    private String userPassword;
    private String userId;
    private String userEmail;
    private LocalDateTime userSignDate;
}
