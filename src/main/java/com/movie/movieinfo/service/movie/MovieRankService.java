package com.movie.movieinfo.service.movie;

import com.movie.movieinfo.dto.movie.movieRank.MovieRankApiResponseDto;
import com.movie.movieinfo.dto.movie.movieRank.MovieRankResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieRankService {

    private final RestTemplate restTemplate;
    private final String baseUrl = "http://kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json";
    private final String key = "1d0c83284fa09d1173eb87e683c896ee";

    public List<MovieRankResponseDto> getRank() {
        String url = baseUrl + "?key=" + key;
        try {
            // API 호출 + 응답
            MovieRankApiResponseDto response = restTemplate.getForObject(url, MovieRankApiResponseDto.class);

            return response.getMovieListResult().getMovieList().stream().map(movieInfo -> new MovieRankResponseDto(
                    movieInfo.getMovieCd(),
                    movieInfo.getMovieNm(),
                    movieInfo.getOpenDt(),
                    movieInfo.getDirectorNm()
            )).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList(); // 오류 발생 시 빈 리스트 반환
    }
}
