package com.movie.movieinfo.dto.movie.movieList;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
public class MovieListRequestDto {
    /**영화 목록 요청 할때 사용되는 파라미터들
     * API키 제외 Optional*/
    private  String movieName;
}
