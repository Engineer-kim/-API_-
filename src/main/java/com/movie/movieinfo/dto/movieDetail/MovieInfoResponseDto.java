package com.movie.movieinfo.dto.movieDetail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Builder
@AllArgsConstructor
@Jacksonized
@Getter
@Setter
public class MovieInfoResponseDto {
    /**응답 받을 파라미터(객체)들*/
    private String movieCd;
    private String movieNm;
    private String movieNmEn;
    private String prdtYear;
    private String openDt;
    private String typeNm;
    private String prdtStatNm;
    private String nationAlt;
    private String genreAlt;
    private String repNationNm;
    private String repGenreNm;
    private String directors;
    private String companys;
}
