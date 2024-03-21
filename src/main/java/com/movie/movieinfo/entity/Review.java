package com.movie.movieinfo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column(name = "seq", nullable = false ,unique = true)
    private Integer seq;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "starCount", nullable = false)
    private float starCount;

    @Column(name = "detail", nullable = false, length = 300)
    private String detail;

    @Column(name = "user_id", nullable = false, length = 100)
    private String userId;

    @Column(name = "movie_cd", nullable = false, length = 100)
    private String movieCd;

}
