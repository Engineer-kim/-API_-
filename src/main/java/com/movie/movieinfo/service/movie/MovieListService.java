package com.movie.movieinfo.service.movie;


import com.movie.movieinfo.dto.movie.movieList.MovieListRequestDto;
import com.movie.movieinfo.dto.movie.movieList.MovieListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieListService {
    private final RestTemplate restTemplate;
    private final String baseUrl = "http://kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json";
    private final String key = "1d0c83284fa09d1173eb87e683c896ee";

    public List<MovieListResponseDto> getList(MovieListRequestDto request) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("key", key)
                .queryParam("movieName", request.getMovieName())
                .toUriString();
        return null;
    }
}
