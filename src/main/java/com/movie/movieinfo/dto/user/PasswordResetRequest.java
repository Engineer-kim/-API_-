package com.movie.movieinfo.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetRequest {
    private String userId;
    private String email;
}
