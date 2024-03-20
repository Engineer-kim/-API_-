package com.movie.movieinfo.dto.movie.movieList;

import com.movie.movieinfo.dto.movie.Company;
import com.movie.movieinfo.dto.movie.Director;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Movie {
    private String movieCd; // 영화 코드
    private String movieNm; // 영화 이름
    private String movieNmEn; // 영화 영문 이름
    private String prdtYear; // 제작 연도
    private String openDt; // 개봉일
    private String genreAlt; // 장르
    private List<Director> directors; // 감독 목록
    private List<Company> companies; // 제작사 목록
    
}
