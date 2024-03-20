package com.movie.movieinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@AllArgsConstructor
@Jacksonized
public class MovieInfoDto {
    private String itemPerPage;
    private String movieName;
    private String directorName;
    private String openStartDay;
    private String openEndDate;
    private String prdStartYear;
    private String prdEndYear;
    private String repNationCd;
    private String movieTypeCd;
}
