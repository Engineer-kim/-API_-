package com.movie.movieinfo.dto.movie;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Company {
    /**제작사 코드(식별번호)*/
    private String companyCd;
    /**제작사 명*/
    private String companyNm;

    public Company(String companyCd, String companyNm) {
        this.companyCd = companyCd;
        this.companyNm = companyNm;
    }
}
