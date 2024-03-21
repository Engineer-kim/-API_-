package com.movie.movieinfo.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinRequestDto {
    private String userName;
    private String userPassword;
    private String userId;
    private String userEmail;
}
