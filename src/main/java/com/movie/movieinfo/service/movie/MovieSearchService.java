package com.movie.movieinfo.service.movie;

import com.movie.movieinfo.dto.movie.movieList.MovieList;
import com.movie.movieinfo.dto.movie.movieList.MovieListResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.naming.directory.SearchResult;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieSearchService {

    private final RestTemplate restTemplate;
    private final String baseUrl = "http://kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json";
    private final String key = "1d0c83284fa09d1173eb87e683c896ee";

    public List<MovieList> searchMovie(String searchText , String type) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("key", key);
        uriBuilder.queryParam("type", type);

        switch (type) {
            case "movieNm":
                uriBuilder.queryParam("movieNm", searchText);
                break;
            case "directorNm":
                uriBuilder.queryParam("directorNm", searchText);
                break;
            default:
                throw new IllegalArgumentException("Invalid search type: " + type); // 잘못된 타입 처리
        }

        //restTempate 은 한글과 같은 비아스키 코드는 인식을 못하므로 인코딩 가능하도록 설정
        URI url = uriBuilder.build().toUri();
        log.info("API Request URL:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: " + url);

        try {
            MovieListResponseDto response = restTemplate.getForObject(url, MovieListResponseDto.class);
            if (response != null && response.getMovieListResult() != null) {
                System.out.println("response.getMovieListResult().getMovieList():::::::::::"+ response.getMovieListResult().getMovieList());
                List<MovieList> results = response.getMovieListResult().getMovieList();
                return results.stream().limit(10).toList();
            }
            System.out.println("response.toString():::::::::::::::::::::::::::::::::::::::::::"+response.toString() );
        } catch (Exception e) {
            log.error("Reason For Error:::::::::::::::::::::::::::::::::::::" + e);
            e.printStackTrace();
        }
        return Collections.emptyList(); //이상한 검색어로 검색 발생 시 빈 리스트 반환
    }
}
