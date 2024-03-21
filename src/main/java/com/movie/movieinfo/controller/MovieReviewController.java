package com.movie.movieinfo.controller;

import com.movie.movieinfo.dto.movie.movieDetail.response.MovieInfoResponseWrapperDto;
import com.movie.movieinfo.dto.review.ReviewDto;
import com.movie.movieinfo.service.movie.MovieReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movieReview")
@RequiredArgsConstructor
public class MovieReviewController {

    private final MovieReviewService movieReviewService;

    /**리뷰 작성
     * 데이터 존재시 수정으로 판단 -> 수정 쿼리 실행
     * 데이터 미존재 신규 작성으로 판단 -> 인서트 쿼리 실행
     * */
    @PostMapping("/v1/saveMovieReview")
    public ResponseEntity<?> saveMovieReview(@RequestBody ReviewDto reviewDto) {
        if(reviewDto.getUserId().isEmpty()){ //유저 아이디가 없을때 인서트 및 수정 안되도록 ,-> 세션 과 더불어 더블체크
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인후 시도 해주세요");
        }
         response = movieReviewService.saveReview(reviewDto);
        return ResponseEntity<response>.;
    }

    /**리뷰 조회
     * 데이터 있는지부터 체크
     * 있다면 -> 조회한거 보여주기
     * 없다면 ->  커스텀 에러
     * */
    @GetMapping("/v1/getMovieReview")
    public ResponseEntity<ReviewDto> getMovieReview(@RequestBody ReviewDto reviewDto) {
        if(reviewDto.getUserId().isEmpty()){ //유저 아이디가 없을때 인서트 및 수정 안되도록 ,-> 세션 과 더불어 더블체크
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인후 시도 해주세요");
        }
        return null;
    }


    /**리뷰 삭제
     * 없는데 삭제하려면 오류가 나니
     * 데이터 있는지부터 체크
     * 있다면 -> 삭제
     * 없다면 -> 커스텀 에러
     * */
    @DeleteMapping("/v1/removeMovieReview")
    public ResponseEntity<ReviewDto> removeMovieReview() {
        return null;
    }

}
