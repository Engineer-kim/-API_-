package com.movie.movieinfo.dto.movie.movieRank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Builder
@AllArgsConstructor
@Jacksonized
@Getter
@Setter
public class MovieRankResponseDto {
    /**박스오피스 조회(일별 순위) 일자 응답파라미터들 */
    private String showRange;
    /**순위*/
    private  String rank;
    /**전일 대비 순위 증감분*/
    private String rankInten;
    /** 랭킹 신규 진입 여부*/
    private String rankOldAndNew;
    /**영화 코드*/
    private String movieCd;
    /**영화 국문 명*/
    private String movieNm;
    /**영화 개봉일*/
    private String openDt;
    /**해당일 관객수*/
    private String audiCnt;
    /**전일 대비 관객수 증감비율*/
    private String audiChange;
    /**해당일자에 상영된 횟수*/
    private String showCnt;

}
