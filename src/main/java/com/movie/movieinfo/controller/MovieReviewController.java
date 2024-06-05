package com.movie.movieinfo.controller;

import com.movie.movieinfo.annotation.UserIdNotEmptyInterface;
import com.movie.movieinfo.dto.review.ReviewDto;
import com.movie.movieinfo.entity.Review;
import com.movie.movieinfo.service.movie.MovieReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movieReview")
@RequiredArgsConstructor
@Slf4j
public class MovieReviewController {

    private final MovieReviewService movieReviewService;

    /**
     * 리뷰 작성
     * 데이터 존재시 수정으로 판단 -> 수정 쿼리 실행
     * 데이터 미존재 신규 작성으로 판단 -> 인서트 쿼리 실행
     */
    @PostMapping("/v1/saveMovieReview")
    public ResponseEntity<String> saveMovieReview(@RequestBody ReviewDto reviewDto) {
        ResponseEntity<String> response = movieReviewService.saveReview(reviewDto);
        return response;
    }

    /**
     * 리뷰 조회
     * 데이터 있는지부터 체크
     * 있다면 -> 조회한거 보여주기
     * 없다면 ->  커스텀 에러
     * 단건 출력
     * 해당 유저가 작성한 리뷰가 없다면 404 반환
     * 있다면 200및 리뷰내용, 별점 등등의 정보 넘어감
     */
    @GetMapping("/v1/getMovieReview")
    public ResponseEntity<Review> getMovieReview(@UserIdNotEmptyInterface @RequestParam("userId") String userId, @RequestParam("movieCd") String movieCd) {
        Optional<Review> hasReview = movieReviewService.findUserReview(userId, movieCd);
        return hasReview.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    /**
     * 리뷰 삭제
     * 없는데 삭제하려면 오류가 나니
     * 데이터 있는지부터 체크
     * 있다면 -> 삭제
     * 없다면 -> 커스텀 에러
     * 단건 출력
     */
    @DeleteMapping("/v1/removeMovieReview")
    public ResponseEntity<String> removeMovieReview(@UserIdNotEmptyInterface @RequestParam("userId") String userId, @RequestParam("movieCd") String movieCd) {
        boolean isDeleted = movieReviewService.deleteReview(userId, movieCd);
        if (!isDeleted) {
            // 삭제 실패 시(해당 회원이 작성한 리뷰가 없는 경우)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 과정 중 오류가 발생했습니다");
        }
        // 삭제 성공시
        return ResponseEntity.ok("리뷰가 성공적으로 삭제되었습니다");
    }


    /**
    *  세션없어도 비로그인사용자가 들어와도
     * 다른사람들이(세션있는사용자들) 이미 달아놓은 리뷰 볼수있게끔 하는 API
    **/
    @GetMapping("/v1/getMovieAllReview")
    public List<ReviewDto> getMovieAllReviewWithoutNoSession(@RequestParam("movieCd") String movieCd) {
        return movieReviewService.findUserReviewWithoutNoSession(movieCd);
    }
}
