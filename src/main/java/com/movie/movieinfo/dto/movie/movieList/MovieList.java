package com.movie.movieinfo.dto.movie.movieList;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.movie.movieinfo.dto.movie.Company;
import com.movie.movieinfo.dto.movie.Director;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovieList {
    /**영화 목록 응답객체*/
    @JsonProperty("movieCd")
    private String movieCode;

    @JsonProperty("movieNm")
    private String movieNameKorean;

    @JsonProperty("movieNmEn")
    private String movieNameEnglish;

    @JsonProperty("prdtYear")
    private String productionYear;

    @JsonProperty("openDt")
    private String openDate;

    @JsonProperty("typeNm")
    private String movieType;

    @JsonProperty("prdtStatNm")
    private String productionStatus;

    @JsonProperty("nationAlt")
    private String productionCountries;

    @JsonProperty("genreAlt")
    private String genres;

    @JsonProperty("repNationNm")
    private String representativeCountryName;

    @JsonProperty("repGenreNm")
    private String representativeGenreName;

    @JsonProperty("directors")
    private List<Director> directors;

    @JsonProperty("companys")
    private List<Company> companies;
}
