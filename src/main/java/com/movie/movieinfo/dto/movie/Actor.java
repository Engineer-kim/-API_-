package com.movie.movieinfo.dto.movie;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Actor {
    /**배우 이름*/
    private String peopleNm; 
    /**배우 영문이름*/
    private String peopleNmEn; 
}
