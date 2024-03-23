package com.movie.movieinfo.service.movie;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.movieinfo.dto.movie.movieDetail.response.MovieInfoResponseWrapperDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class MovieDetailService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Cacheable(value = "movieDetails", key = "#movieCd")
    public MovieInfoResponseWrapperDto getDetail(String movieCd) {
        final String baseUrl = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json";

        final String key = "1d0c83284fa09d1173eb87e683c896ee";


        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("key", key)
                .queryParam("movieCd", movieCd);

        System.out.println(uriBuilder.toUriString());
        String responseString = restTemplate.getForObject(uriBuilder.toUriString(), String.class);
        try {
            MovieInfoResponseWrapperDto response = objectMapper.readValue(responseString, MovieInfoResponseWrapperDto.class);
            return response;
        } catch (Exception e) {
            e.getMessage();
            e.getStackTrace();
        }
        return null;

    }
}
