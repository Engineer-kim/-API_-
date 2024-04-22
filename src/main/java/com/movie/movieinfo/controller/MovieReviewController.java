package com.movie.movieinfo.controller;

import com.movie.movieinfo.annotation.UserIdNotEmptyInterface;
import com.movie.movieinfo.annotation.login.LoggedIn;
import com.movie.movieinfo.dto.review.ReviewDto;
import com.movie.movieinfo.service.movie.MovieReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

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
    @LoggedIn
    public ResponseEntity<String> saveMovieReview(@RequestBody ReviewDto reviewDto) {
        if (reviewDto.getUserId().isEmpty() || reviewDto.getUserId() == null) {
            //유저 아이디가 없을때 인서트 및 수정 안되도록 ,-> 세션 과 더불어 더블체크
            return ResponseEntity.status(UNAUTHORIZED).body("로그인후 시도 해주세요");
        }
        ResponseEntity<String> response = movieReviewService.saveReview(reviewDto);
        return response;
    }

    /**
     * 리뷰 조회
     * 데이터 있는지부터 체크
     * 있다면 -> 조회한거 보여주기
     * 없다면 ->  커스텀 에러
     * 단건 출력
     */
    @GetMapping("/v1/getMovieReview")
    @LoggedIn
    public boolean getMovieReview(@UserIdNotEmptyInterface @RequestParam("userId") String userId, @RequestParam("movieCd") String movieCd) {
        boolean hasReview = movieReviewService.findUserReview(userId, movieCd);
        if (!hasReview) {
            return false;
        }
        return true;
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
    @LoggedIn
    public boolean removeMovieReview(@UserIdNotEmptyInterface @RequestParam("userId") String userId, @RequestParam("movieCd") String movieCd) {
        boolean isDeleted = movieReviewService.deleteReview(userId, movieCd);
        if (!isDeleted) {
            // 삭제 실패 시(해당 회원이 작성한 리뷰가 없는 경우)
            return false;
        }
        // 삭제 성공시
        return true;
    }
}
