package com.movie.movieinfo.dto.movie.movieRegion.response;

import com.movie.movieinfo.dto.movie.movieRegion.MovieRegionCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegionDailyRankResponse {
    private MovieRegionCode regionInfo;
    private Object dailyRankInfo; // 실제 타입으로 대체해야 함

    public RegionDailyRankResponse(MovieRegionCode regionInfo, Object dailyRankInfo) {
        this.regionInfo = regionInfo;
        this.dailyRankInfo = dailyRankInfo;
    }
}
