package com.movie.movieinfo.controller;

import com.movie.movieinfo.dto.movie.movieDetail.response.MovieInfoResponseWrapperDto;
import com.movie.movieinfo.dto.movie.movieList.MovieList;
import com.movie.movieinfo.dto.movie.movieRank.response.BoxWeeklyOfficeResult;
import com.movie.movieinfo.dto.movie.movieRank.response.MovieDailyRank;
import com.movie.movieinfo.dto.movie.movieRank.response.MovieWeeklyRank;
import com.movie.movieinfo.dto.movie.movieRank.response.MovieWeeklyRankWrapperResponse;
import com.movie.movieinfo.service.movie.*;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movieInfo")
@RequiredArgsConstructor
public class MovieInfoController {

    private  final MovieListService movieListService;
    private  final MovieDetailService movieDetailService;
    private  final MovieDailyRankService movieDailyRankService;
    private  final MovieWeeklyRankService movieWeeklyRankService;
    private  final MovieSearchService movieSearchService;


    /**영화 상세정보 불러오는 엔드포인트(단건 출력)*/

    /**
     * 예시 응답 엔드 포인트
     * http://kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json?key=f5eef3421c602c6cb7ea224104795888
     * */

    @GetMapping("/v1/moviesDetail")
    public ResponseEntity<MovieInfoResponseWrapperDto> getMovieDetailInfo(@RequestParam("movieCd")  String movieCd){
      MovieInfoResponseWrapperDto response = movieDetailService.getDetail(movieCd);
      String moviePosterImageUrl  = CrawlingMoviePoster.getMoviePosterImageUrl(movieCd);
        System.out.println("movieImageUrl::::::::::::::::::::::::::::::::::::::::::::" +moviePosterImageUrl);
        System.out.println(response.toString());
        response.setMoviePosterUrl(moviePosterImageUrl);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**일별 순위, 다건 출력(top1 ~ toP10 (다건 출력))*/

    /**
     * 예시 응답 엔드 포인트
     *	http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=f5eef3421c602c6cb7ea224104795888&targetDt=20120101
     * */
    @GetMapping("/v1/moviesDailyRank")
    public ResponseEntity<List<MovieDailyRank>> showDailyMoviesRank(@RequestParam("targetDt") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate targetDt) {
        List<MovieDailyRank> response = movieDailyRankService.getDailyRank(targetDt);
        System.out.println(response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    /**주별(월~ 일요일까지) 순위 다건 출력(top1 ~ toP10 (다건 출력))*/

    @GetMapping("/v1/moviesWeeklyRank")
    public ResponseEntity<MovieWeeklyRankWrapperResponse> showWeeklyMoviesRank(@RequestParam("targetDt") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate targetDt) {
        Optional<MovieWeeklyRankWrapperResponse> response = movieWeeklyRankService.getWeeklyRank(targetDt);
        return response
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }
    
    /** 영화 목록 엔드포인트(다건 출력)*/

    /**
     * 예시 응답 엔드 포인트
     *	http://kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json?key=f5eef3421c602c6cb7ea224104795888
     * */

    @GetMapping("/v1/moviesList")
    public ResponseEntity<List<MovieList>> getMovieList() {
        List<MovieList> response = movieListService.getList();
        System.out.println(response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**영화 포스터 가져오는(크롤링) API 임*/
    @GetMapping("/v1/getMoviePosterImage")
    public String getMoviePosterImageUrl(@RequestParam("movieCd") String movieCd){
        String response = CrawlingMoviePoster.getMoviePosterImageUrl(movieCd);
        return  response;
    }

    /**감독명 또는 영화제목으로 검색 가능한 API(Like 쿼리로 검색)*/
    @GetMapping("/v1/movieSearch")
    public ResponseEntity<List<MovieList>> searchMovie(@RequestParam(value = "movieNm", required = false) String movieName, @RequestParam(value = "directorNm", required = false) String directorName) {
        List<MovieList> response = movieSearchService.searchMovie(movieName, directorName);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    

    /**임시 로그아웃 테스트르르위한 엔드포인트*/
    @GetMapping("/main")
    public String main() {
        return "main";
    }

}
