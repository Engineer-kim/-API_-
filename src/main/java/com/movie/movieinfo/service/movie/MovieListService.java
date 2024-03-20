package com.movie.movieinfo.service.movie;


import com.movie.movieinfo.dto.movie.Company;
import com.movie.movieinfo.dto.movie.Director;
import com.movie.movieinfo.dto.movie.movieList.Movie;
import com.movie.movieinfo.dto.movie.movieList.MovieListRequestDto;
import com.movie.movieinfo.dto.movie.movieList.MovieListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

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

        ResponseEntity<List<Movie>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Movie>>() {});
        List<Movie> movies = response.getBody();

        return movies.stream().map(movie -> new MovieListResponseDto(
                movie.getMovieCd(),
                movie.getMovieNm(),
                movie.getMovieNmEn(),
                movie.getPrdtYear(),
                movie.getOpenDt(),
                movie.getGenreAlt(),
                movie.getDirectors().stream().map(director -> new Director(
                        director.getPeopleNm(),
                        director.getPeopleNmEn()
                )).collect(Collectors.toList()),
                movie.getCompanies().stream().map(company -> new Company(
                        company.getCompanyCd(),
                        company.getCompanyNm()
                )).collect(Collectors.toList())
        )).collect(Collectors.toList());
    }
}
