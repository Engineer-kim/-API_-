package com.movie.movieinfo.dto.movie.movieList;

import com.movie.movieinfo.dto.movie.Company;
import com.movie.movieinfo.dto.movie.Director;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Setter
public class MovieListResponseDto {
    /**영화 목록 응답객체*/
    
    
    /**영화 코드(식별자)*/
    private String movieCd;
    /**영화명 국문명*/
    private String movieNm;
    /**영화명 영문명*/
    private String movieNmEn;
    /**제작연도*/
    private String prdtYear;
    /**개봉일*/
    private String openDt;
    /**장르*/
    private String genreAlt;
    /**감독*/
    private List<Director> directors;
    /**제작사*/
    private List<Company> companies;

    public MovieListResponseDto(String movieCd, String movieNm, String movieNmEn, String prdtYear,
                                String openDt, String genreAlt, List<Director> directors,
                                List<Company> companies) {
        this.movieCd = movieCd;
        this.movieNm = movieNm;
        this.movieNmEn = movieNmEn;
        this.prdtYear = prdtYear;
        this.openDt = openDt;
        this.genreAlt = genreAlt;
        this.directors = directors;
        this.companies = companies;
    }

}
