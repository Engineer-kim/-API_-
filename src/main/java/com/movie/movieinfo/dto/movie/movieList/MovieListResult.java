package com.movie.movieinfo.dto.movie.movieList;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovieListResult {
    private String totCnt;
    private String source;
    private List<MovieList> movieList;
}
