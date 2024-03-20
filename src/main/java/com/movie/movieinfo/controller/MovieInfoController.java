package com.movie.movieinfo.controller;

import com.movie.movieinfo.dto.movie.movieDetail.MovieInfoRequestDto;
import com.movie.movieinfo.dto.movie.movieDetail.MovieInfoResponseDto;
import com.movie.movieinfo.dto.movie.movieList.MovieListRequestDto;
import com.movie.movieinfo.dto.movie.movieList.MovieListResponseDto;
import com.movie.movieinfo.dto.movie.movieRank.MovieRankResponseDto;
import com.movie.movieinfo.service.movie.MovieDetailService;
import com.movie.movieinfo.service.movie.MovieListService;
import com.movie.movieinfo.service.movie.MovieRankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movieInfo/")
@RequiredArgsConstructor
public class MovieInfoController {

    private  final MovieListService movieListService;
    private  final MovieDetailService movieDetailService;
    private  final MovieRankService movieRankService;


    /**영화 상세정보 불러오는 엔드포인트(단건 출력)*/

    /**
     * 예시 응답 엔드 포인트
     * http://kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json?key=f5eef3421c602c6cb7ea224104795888
     * */

    @GetMapping("/v1/moviesDetail")
    public ResponseEntity<MovieInfoResponseDto> getMovieDetailInfo(@RequestParam MovieInfoRequestDto request) {
      MovieInfoResponseDto response = movieDetailService.getDetail(request);
        System.out.println(response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**일별 박스오피스(오늘의 순위) 엔드포인트(다건 출력)*/

    /**
     * 예시 응답 엔드 포인트
     *	http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=f5eef3421c602c6cb7ea224104795888&targetDt=20120101
     * */

    @GetMapping("/v1/moviesRank")
    public ResponseEntity<List<MovieRankResponseDto>> showDailyMoviesRank() {
        List<MovieRankResponseDto> response = movieRankService.getRank();
        System.out.println(response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /** 영화 목록 엔드포인트(다건 출력)*/

    /**
     * 예시 응답 엔드 포인트
     *	http://kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json?key=f5eef3421c602c6cb7ea224104795888
     * */

    @GetMapping("/v1/moviesList")
    public ResponseEntity<List<MovieListResponseDto>> getMovieList(@RequestParam MovieListRequestDto request) {
        List<MovieListResponseDto> response = movieListService.getList(request);
        System.out.println(response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
