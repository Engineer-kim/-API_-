package com.movie.movieinfo.dto.movie.movieList;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.movie.movieinfo.dto.movie.Company;
import com.movie.movieinfo.dto.movie.Director;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Setter
public class MovieListResponseDto {
    private MovieListResult movieListResult;
}
