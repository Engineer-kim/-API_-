package com.movie.movieinfo.dto.movie;

import lombok.Data;

@Data
public class Company {
    /**제작사 코드(식별번호)*/
    private String companyCd;
    /**제작사 명*/
    private String companyNm;
}
