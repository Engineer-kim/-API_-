package com.movie.movieinfo.dto.movie.movieRegion.response;

import com.movie.movieinfo.dto.movie.movieRegion.MovieRegionCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RegionCodesResponse {
    private List<MovieRegionCode> codes;
}