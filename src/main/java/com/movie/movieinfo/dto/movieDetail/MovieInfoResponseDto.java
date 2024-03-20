package com.movie.movieinfo.dto.movieDetail;

import com.movie.movieinfo.dto.Actor;
import com.movie.movieinfo.dto.Director;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Builder
@AllArgsConstructor
@Jacksonized
@Getter
@Setter
public class MovieInfoResponseDto {
    /**응답 받을 파라미터(객체)들*/

    /**영화 코드*/
    private String movieCd;
    /**국문 영화명 */
    private String movieNm;
    /**영문 영화명*/
    private String movieNmEn;
    /**원문 영화명*/
    private String movieNmOg;
    /**제작연도*/
    private String prdtYear;
    /**상영기간*/
    private String showTm;
    /**개봉연도*/
    private String openDt;
    /**유형명*/
    private String typeNm;
    /**제작상태명*/
    private String prdtStatNm;
    /**제작 국가명*/
    private String nationNm;
    /**장르*/
    private List<String> genre;
    /**감독*/
    private List<Director> directors;
    /**배우명*/
    private List<Actor> actors;
    /**관람등급명*/
    private String watchGradeNm;

}
