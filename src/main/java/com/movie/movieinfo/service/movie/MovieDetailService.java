package com.movie.movieinfo.service.movie;

import com.movie.movieinfo.dto.movie.movieDetail.MovieInfoRequestDto;
import com.movie.movieinfo.dto.movie.movieDetail.MovieInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieDetailService {
    public MovieInfoResponseDto getDetail(MovieInfoRequestDto request) {
        return MovieInfoResponseDto.builder().build();
    }
}
