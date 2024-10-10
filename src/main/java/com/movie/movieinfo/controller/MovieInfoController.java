package com.movie.movieinfo.controller;

import com.movie.movieinfo.annotation.login.LoggedIn;
import com.movie.movieinfo.dto.movie.movieDetail.response.MovieInfoResponseWrapperDto;
import com.movie.movieinfo.dto.movie.movieList.MovieList;
import com.movie.movieinfo.dto.movie.movieRank.response.MovieDailyRank;
import com.movie.movieinfo.dto.movie.movieRank.response.MovieWeeklyRankWrapperResponse;
import com.movie.movieinfo.dto.movie.movieRegion.MovieRegionCode;
import com.movie.movieinfo.dto.movie.movieRegion.response.RegionDailyRankResponse;
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

    private final MovieListService movieListService;
    private final MovieDetailService movieDetailService;
    private final MovieDailyRankService movieDailyRankService;
    private final MovieWeeklyRankService movieWeeklyRankService;
    private final MovieSearchService movieSearchService;
    private final MovieRegionInfoService movieRegionInfoService;
    private final MovieReviewService movieReviewService;
    private final CrawlingMoviePoster crawlingMoviePoster;


    /**영화 상세정보 불러오는 엔드포인트(단건 출력)*/

    /**
     * 예시 응답 엔드 포인트
     * http://kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json?key=f5eef3421c602c6cb7ea224104795888
     */

    @GetMapping("/v1/moviesDetail")
    public ResponseEntity<MovieInfoResponseWrapperDto> getMovieDetailInfo(@RequestParam("movieCd") String movieCd) {
        MovieInfoResponseWrapperDto response = movieDetailService.getDetail(movieCd);
        String moviePosterImageUrl = crawlingMoviePoster.getMoviePosterImageUrl(movieCd);
        System.out.println("movieImageUrl::::::::::::::::::::::::::::::::::::::::::::" + moviePosterImageUrl);
        System.out.println(response.toString());
        response.setMoviePosterUrl(moviePosterImageUrl);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**일별 순위, 다건 출력(top1 ~ toP10 (다건 출력))*/

    /**
     * 예시 응답 엔드 포인트
     * http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=f5eef3421c602c6cb7ea224104795888&targetDt=20120101
     */
    @GetMapping("/v1/moviesDailyRank")
    public ResponseEntity<List<MovieDailyRank>> showDailyMoviesRank(@RequestParam("targetDt") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate targetDt) {
        List<MovieDailyRank> response = movieDailyRankService.getDailyRank(targetDt);
        System.out.println(response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 주별(월~ 일요일까지) 순위 다건 출력(top1 ~ toP10 (다건 출력))
     */

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
     * http://kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json?key=f5eef3421c602c6cb7ea224104795888
     */

    @GetMapping("/v1/moviesList")
    public ResponseEntity<List<MovieList>> getMovieList() {
        List<MovieList> response = movieListService.getList();
        System.out.println(response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 영화 포스터 가져오는(크롤링) API 임
     */
    @GetMapping("/v1/getMoviePosterImage")
    public String getMoviePosterImageUrl(@RequestParam("movieCd") String movieCd) {
        String response = crawlingMoviePoster.getMoviePosterImageUrl(movieCd);
        return response;
    }

    /**
     * 감독명 또는 영화제목으로 검색 가능한 API(Like 쿼리로 검색)
     */
    @GetMapping("/v1/movieSearch")
    public ResponseEntity<List<MovieList>> searchMovie(
            @RequestParam(value = "searchText", required = false)  String searchText,
            @RequestParam(value = "type", required = false) String type){
            List<MovieList>response = movieSearchService.searchMovie(searchText ,type);
            return new ResponseEntity<>(response, HttpStatus.OK);
    }

            /**지역 별 영화 정보 출력
             * 주간 순위 정보 (TOP1 ~ TOP10)
             * +
             * 지역별코드 내려갈수있겠끔
             * **/

            @GetMapping("/v1/{comCode}/listRegionMovieRankInfo")
            public ResponseEntity<?>getMovieRankInfoWithRegionInfo(@RequestParam("targetDt") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate targetDt, @PathVariable String comCode) {
        Optional<MovieRegionCode> optionalRegionInfo = movieRegionInfoService.getRegionCode(comCode);
        System.out.println("optionalRegionInfo:::::::::::::::::::::::::::" + optionalRegionInfo.toString());
        List<MovieDailyRank> dailyRankInfo = movieDailyRankService.getDailyRankWithRegionCode(targetDt, comCode);
        System.out.println("dailyRankInfo.toString()::::::::::::::::::::::::::::" + dailyRankInfo.toString());
        if (optionalRegionInfo.isPresent()) {
            MovieRegionCode regionInfo = optionalRegionInfo.get();
            RegionDailyRankResponse response = new RegionDailyRankResponse(regionInfo, dailyRankInfo);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 임시 로그아웃 테스트르르위한 엔드포인트
     */
    @GetMapping("/main")
    public String main() {
        return "main";
    }


    /**
     * 해당 영화에 달린 리뷰 갯수 조회 API(세션이랑은 상관이 없어야함)
     */
    @GetMapping("/v1/getMovieReviewTotalCount")
    public Integer getMovieReviewTotalCount(@RequestParam("movieCd") String movieCd) {
        return movieReviewService.getMovieReviewCount(movieCd);
    }
}
