package com.movie.movieinfo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq", nullable = false ,unique = true)
    private Integer seq;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "star_count", nullable = false)
    private float starCount;

    @Column(name = "detail", nullable = false, length = 300)
    private String detail;

    @Column(name = "user_id", nullable = false, length = 100)
    private String userId;

    @Column(name = "movie_cd", nullable = false, length = 100)
    private String movieCode;

}
