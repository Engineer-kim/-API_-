package com.movie.movieinfo.service.movie;

import com.movie.movieinfo.Repository.MovieReviewRepository;
import com.movie.movieinfo.dto.review.ReviewDto;
import com.movie.movieinfo.entity.Review;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieReviewService {

    private final MovieReviewRepository movieReviewRepository;

    public ResponseEntity<String> saveReview(ReviewDto reviewDto) {
        //데이터 있는지 찿기 , 있다면 수정 없다면 인서트
        Optional<Review> existReview = movieReviewRepository.findByUserIdAndMovieCode(reviewDto.getUserId(), reviewDto.getMovieCd());
        if (!existReview.isPresent()) {
            // 새 리뷰 데이터로 판단 -> 삽입 로직
            Review newReview = convertDtoToEntity(reviewDto);
            try {
                Review save =  movieReviewRepository.save(newReview);
                log.info(newReview.toString());
                log.info(save.toString());
                return new ResponseEntity<>("Insert Success", HttpStatus.CREATED);
            } catch (Exception e) {
                e.getMessage();
                return new ResponseEntity<>("Insert Fail", HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
        //찿은 데이터 있으므로 업데아트 로직 -> 글 수정 로직으로
        try {
           Review update = updateExistingReview(existReview.get(), reviewDto);
            log.info(existReview.get().toString());
            log.info(update.toString());
            return new ResponseEntity<>("Update Success", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.getMessage();
            return new ResponseEntity<>("Update Fail", HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    // ReviewDto를 Review 엔티티로 변환하는 로직
    private Review convertDtoToEntity(ReviewDto reviewDto) {
        try {
            Review review = Review.builder()
                    .movieCd(reviewDto.getMovieCd())
                    .userId(reviewDto.getUserId())
                    .starCount(reviewDto.getStarCount())
                    .seq(reviewDto.getSeq())
                    .title(reviewDto.getTitle())
                    .detail(reviewDto.getDetail())
                    .build();
            log.info(review.toString());
            return review;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("DTO를 Entity로 변환하는 과정에서 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    //리뷰 수정하는 메서드
    private Review updateExistingReview(Review existingReview, ReviewDto reviewDto) {
        existingReview.setDetail(reviewDto.getDetail());
        existingReview.setStarCount(reviewDto.getStarCount());
        existingReview.setTitle(reviewDto.getTitle());
        return movieReviewRepository.save(existingReview);
    }

    public ResponseEntity<?> findUserReview(String userId, String movieCd) {
        Optional<Review> existReview = movieReviewRepository.findByUserIdAndMovieCode(userId, movieCd);
        if (!existReview.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 회원의 리뷰를 찾을 수 없습니다.");
        }
        return new ResponseEntity<>(existReview, HttpStatus.OK);
    }
}
