package com.movie.movieinfo.controller;

import com.movie.movieinfo.dto.movie.movieDetail.response.MovieInfoResponseWrapperDto;
import com.movie.movieinfo.dto.movie.movieList.MovieList;
import com.movie.movieinfo.dto.movie.movieRank.response.MovieRank;
import com.movie.movieinfo.service.movie.CrawlingMoviePoster;
import com.movie.movieinfo.service.movie.MovieDetailService;
import com.movie.movieinfo.service.movie.MovieListService;
import com.movie.movieinfo.service.movie.MovieRankService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/movieInfo")
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
    public ResponseEntity<MovieInfoResponseWrapperDto> getMovieDetailInfo(@RequestParam("movieCd")  String movieCd){
      MovieInfoResponseWrapperDto response = movieDetailService.getDetail(movieCd);
      String moviePosterImageUrl  = CrawlingMoviePoster.getMoviePosterImageUrl(movieCd);
        System.out.println("movieImageUrl::::::::::::::::::::::::::::::::::::::::::::" +moviePosterImageUrl);
        System.out.println(response.toString());
        response.setMoviePosterUrl(moviePosterImageUrl);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**일별 박스오피스(오늘의 순위) 엔드포인트(다건 출력)*/

    /**
     * 예시 응답 엔드 포인트
     *	http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=f5eef3421c602c6cb7ea224104795888&targetDt=20120101
     * */

    @GetMapping("/v1/moviesRank")
    public ResponseEntity<List<MovieRank>> showDailyMoviesRank(@RequestParam("targetDt") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate targetDt) {
        List<MovieRank> response = movieRankService.getRank(targetDt);
        System.out.println(response);
        return new ResponseEntity<>(response, HttpStatus.OK);
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



    /**임시 로그아웃 테스트르르위한 엔드포인트*/
    @GetMapping("/main")
    public String main() {
        return "main";
    }

}
