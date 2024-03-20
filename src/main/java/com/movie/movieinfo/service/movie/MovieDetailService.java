package com.movie.movieinfo.service.movie;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.movieinfo.dto.movie.movieDetail.MovieInfoRequestDto;
import com.movie.movieinfo.dto.movie.movieDetail.MovieInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class MovieDetailService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public MovieInfoResponseDto getDetail(MovieInfoRequestDto request) {
        final  String baseUrl = "http://kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json";

        final String key = "1d0c83284fa09d1173eb87e683c896ee";

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("key", key)
                .queryParam("movieNm", request.getMovieName());

        String response = restTemplate.getForObject(uriBuilder.toUriString(), String.class);


        try {
            // JSON 문자열을 MovieInfoResponseDto 객체로 변환
            MovieInfoResponseDto movieInfoResponseDto = objectMapper.readValue(response, MovieInfoResponseDto.class);
            return movieInfoResponseDto;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
