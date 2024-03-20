package com.movie.movieinfo.dto.movieDetail;

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
public class MovieInfoRequestDto {
    /**요청 할때 사용되는 파라미터들
     * API키 제외 Optional*/
    private String movieName;
}