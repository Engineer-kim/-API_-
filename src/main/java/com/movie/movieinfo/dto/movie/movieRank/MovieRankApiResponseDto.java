package com.movie.movieinfo.dto.movie.movieRank;

import com.movie.movieinfo.controller.MovieInfoController;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieRankApiResponseDto {

    private String movieCd;
    private String movieNm;
    private String openDt;
    private String directorNm;



    public MovieRankApiResponseDto(String movieCd, String movieNm, String openDt, String directorNm) {
        this.movieCd = movieCd;
        this.movieNm = movieNm;
        this.openDt = openDt;
        this.directorNm = directorNm;
    }


}
