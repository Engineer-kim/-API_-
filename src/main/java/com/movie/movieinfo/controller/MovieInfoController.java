package com.movie.movieinfo.controller;

import com.movie.movieinfo.dto.MovieRequestInfoDto;
import com.movie.movieinfo.dto.MovieInfoResponseDto;
import com.movie.movieinfo.service.MovieInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/movieInfo/")
@RequiredArgsConstructor
public class MovieInfoController {

    private  final MovieInfoService movieInfoService;

    @GetMapping("/movies")
    public Mono<ResponseEntity<List<MovieInfoResponseDto>>> getMovies(MovieRequestInfoDto movieRequestInfoDto) {
        Mono<List<MovieInfoResponseDto>> movieListMono = movieInfoService.getAllMovieList(movieRequestInfoDto);
        return movieListMono
                .map(movieList -> ResponseEntity.ok(movieList)) // ResponseEntity에 상태 코드와 함께 데이터를 담아 반환
                .defaultIfEmpty(ResponseEntity.notFound().build()); // 비어있는 경우, 404 Not Found 반환
    }


    /**
     * 예시 응답 엔드 포인트
     * http://kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json?key=f5eef3421c602c6cb7ea224104795888
     * */
}
