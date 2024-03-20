package com.movie.movieinfo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.service.annotation.GetExchange;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String password;
    private String userName;
}