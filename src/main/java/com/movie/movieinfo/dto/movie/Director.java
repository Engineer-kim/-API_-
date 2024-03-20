package com.movie.movieinfo.dto.movie;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class Director {
    /**감독의 이름*/
    private String peopleNm;
    /**김독의 영문이름(있을수도 없을수도 있음)*/
    private Optional<String> peopleNmEn;


    public Director(String peopleNm, Optional<String> peopleNmEn) {
        this.peopleNm = peopleNm;
        this.peopleNmEn = peopleNmEn;
    }
}
