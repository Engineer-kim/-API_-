package com.movie.movieinfo.service.movie;


import com.movie.movieinfo.dto.movie.Company;
import com.movie.movieinfo.dto.movie.Director;
import com.movie.movieinfo.dto.movie.movieList.MovieApiResponse;
import com.movie.movieinfo.dto.movie.movieList.MovieListRequestDto;
import com.movie.movieinfo.dto.movie.movieList.MovieListResponseDto;
import lombok.RequiredArgsConstructor;
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

        ResponseEntity<MovieApiResponse> response = restTemplate.getForEntity(url, MovieApiResponse.class);

        return response.getBody().getMovieList().stream().map(movie -> {
            List<Director> directors = movie.getDirectors().stream()
                    .map(director -> new Director(director.getPeopleNm()))
                    .collect(Collectors.toList());
            List<Company> companies = movie.getCompanies().stream()
                    .map(company -> new Company(company.getCompanyNm()))
                    .collect(Collectors.toList());
            return new MovieListResponseDto(
                    movie.getMovieCd(),
                    movie.getMovieNm(),
                    movie.getMovieNmEn(),
                    movie.getPrdtYear(),
                    movie.getOpenDt(),
                    movie.getGenreAlt(),
                    directors,
                    companies);
        }).collect(Collectors.toList());
    }
}
