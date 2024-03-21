package com.movie.movieinfo.dto.movie.movieDetail.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieInfoResultDto {
    @JsonProperty("movieInfo")
    private MovieInfoDto movieInfoDto;
}
