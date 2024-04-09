package com.movie.movieinfo.dto.movie.movieDetail.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.movie.movieinfo.dto.movie.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovieInfoResponseWrapperDto {
    /**영화 상세 정보 응답 받을 파라미터(객체)들*/
    @JsonProperty("movieInfoResult")
    private MovieInfoResultDto movieInfoResultDto;

    private String moviePosterUrl;
}
