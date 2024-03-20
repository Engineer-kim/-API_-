//package com.movie.movieinfo.controller;
//
//import com.movie.movieinfo.dto.movie.movieDetail.MovieInfoResponseDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/movieReview")
//@RequiredArgsConstructor
//public class MovieReviewController {
//
//    /**리뷰 작성
//     * 데이터 존재시 수정으로 판단 -> 수정 쿼리 실행
//     * 데이터 미존재 신규 작성으로 판단 -> 인서트 쿼리 실행
//     * */
//    @PostMapping("/v1/saveMovieReview")
//    public ResponseEntity<MovieInfoResponseDto> saveMovieReview() {
//
//    }
//
//    /**리뷰 조회
//     * 데이터 있는지부터 체크
//     * 있다면 -> 조회한거 보여주기
//     * 없다면 ->  커스텀 에러
//     * */
//    @GetMapping("/v1/getMovieReview")
//    public ResponseEntity<MovieInfoResponseDto> getMovieReview() {
//        //List<MovieInfoResponseDto> movieListMono = movieInfoService.getAllMovieList(movieRequestInfoDto);
//        //System.out.println(movieListMono);
//        //return new ResponseEntity<>(movieListMono, HttpStatus.OK);
//    }
//
//
//    /**리뷰 삭제
//     * 없는데 삭제하려면 오류가 나니
//     * 데이터 있는지부터 체크
//     * 있다면 -> 삭제
//     * 없다면 -> 커스텀 에러
//     * */
//    @DeleteMapping("/v1/removeMovieReview")
//    public ResponseEntity<MovieInfoResponseDto> removeMovieReview() {
//        //List<MovieInfoResponseDto> movieListMono = movieInfoService.getAllMovieList(movieRequestInfoDto);
//        //System.out.println(movieListMono);
//        //return new ResponseEntity<>(movieListMono, HttpStatus.OK);
//    }
//
//}
