package com.movie.movieinfo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_token")
public class PasswordResetToken {


    @Id
    @Column(name = "userId", nullable = false, length = 100)
    private String userId;

    @Column(name = "token", nullable = false, length = 500)
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(nullable = false, name = "userId")
    private User user;

    @Column(name = "expiryDate", nullable = false)
    private Date expiryDate;
}
