package com.movie.movieinfo.service.movie;


import com.movie.movieinfo.dto.movie.movieList.MovieList;
import com.movie.movieinfo.dto.movie.movieList.MovieListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieListService {
    private final RestTemplate restTemplate;
    private final String baseUrl = "http://kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json";
    private final String key = "1d0c83284fa09d1173eb87e683c896ee";

    public List<MovieList> getList() {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("key", key)
                .toUriString();
        try {
            MovieListResponseDto response = restTemplate.getForObject(url, MovieListResponseDto.class);
            if (response != null && response.getMovieListResult() != null) {
                return response.getMovieListResult().getMovieList();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList(); // 오류 발생 시 빈 리스트 반환
    }
}
