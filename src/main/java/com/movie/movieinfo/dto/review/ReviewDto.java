package com.movie.movieinfo.dto.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    /**리뷰 제목*/
    @JsonProperty("title")
    private String title;
    /**리뷰 별점(1~5까지 , 0.5 ,1.5 ,2.5, 3.5, 4.5까지 표현 가능하도록)*/.
    @JsonProperty("star_count")
    private float starCount;
    /**리뷰 본문*/
    @JsonProperty("detail")
    private String detail;
    /**유저 아이디*/
    @JsonProperty("user_id")
    private String userId;
    /**게시물 순번*/
    @JsonProperty("seq")
    private String seq;
    /**영화 코드*/
    @JsonProperty("movie_cd")
    private String movieCd;
}
