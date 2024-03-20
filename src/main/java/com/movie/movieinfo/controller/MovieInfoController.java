package com.movie.movieinfo.controller;

import com.movie.movieinfo.dto.MovieInfoDto;
import com.movie.movieinfo.service.MovieInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/movieInfo/")
@RequiredArgsConstructor
public class MovieInfoController {

    private  final MovieInfoService movieInfoService;

    @GetMapping("/movies")
    public Mono<ResponseEntity<List<MovieInfoDto>>> getMovies(@RequestParam(required = false) Map<String, String> params) {
        Mono<List<MovieInfoDto>> movieListMono = movieInfoService.getAllMovieList(params);
        return movieListMono.map(movieList -> new ResponseEntity<>(movieList, HttpStatus.OK));
    }

}
