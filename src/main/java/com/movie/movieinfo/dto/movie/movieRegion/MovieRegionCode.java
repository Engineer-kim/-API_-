package com.movie.movieinfo.dto.movie.movieRegion;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MovieRegionCode {
    private String fullCd;
    private String korNm;

    public MovieRegionCode(String fullCd, String korNm) {
        this.fullCd = fullCd;
        this.korNm = korNm;
    }
}
