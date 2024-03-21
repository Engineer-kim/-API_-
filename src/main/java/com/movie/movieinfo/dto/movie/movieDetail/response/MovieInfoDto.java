package com.movie.movieinfo.dto.movie.movieDetail.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.movie.movieinfo.dto.movie.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovieInfoDto {
    @JsonProperty("movieCd")
    private String movieCd;

    @JsonProperty("movieNm")
    private String movieNm;

    @JsonProperty("movieNmEn")
    private String movieNmEn;

    @JsonProperty("movieNmOg")
    private String movieNmOg;

    @JsonProperty("showTm")
    private String showTm;

    @JsonProperty("prdtYear")
    private String prdtYear;

    @JsonProperty("openDt")
    private String openDt;

    @JsonProperty("prdtStatNm")
    private String prdtStatNm;

    @JsonProperty("typeNm")
    private String typeNm;

    @JsonProperty("nations")
    private List<Nation> nations;

    @JsonProperty("genres")
    private List<Genre> genres;

    @JsonProperty("directors")
    private List<Director> directors;

    @JsonProperty("actors")
    private List<Actor> actors;

    @JsonProperty("showTypes")
    private List<ShowType> showTypes;

    @JsonProperty("companys")
    private List<Company> companys;

    @JsonProperty("staffs")
    private List<Staff> staffs;
}
