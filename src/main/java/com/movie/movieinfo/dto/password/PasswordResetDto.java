package com.movie.movieinfo.dto.password;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordResetDto {
    private String token;
    private String newPassword;
}

