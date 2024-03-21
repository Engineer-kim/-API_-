package com.movie.movieinfo.service.movie;

import com.movie.movieinfo.Repository.MovieReviewRepository;
import com.movie.movieinfo.dto.review.ReviewDto;
import com.movie.movieinfo.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieReviewService {

    private final MovieReviewRepository movieReviewRepository;

    public ResponseEntity<?> saveReview(ReviewDto reviewDto) {
        //데이터 있는지 찿기 , 있다면 수정 없다면 인서트
        Optional<Review> existReview = movieReviewRepository.findByUserIdAndMovieCode(reviewDto.getUserId(), reviewDto.getMovieCd());
        if (!existReview.isPresent()) {
            // 새 리뷰 데이터로 판단 -> 삽입 로직
            Review newReview = convertDtoToEntity(reviewDto);
            Review savedReview = movieReviewRepository.save(newReview);
            return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
        }
        //찿은 데이터 있으므로 업데아트 로직 -> 글 수정 로직으로
        Review updatedReview = updateExistingReview(existReview.get(), reviewDto);
        return new ResponseEntity<>(updatedReview, HttpStatus.NO_CONTENT);

    }

    private Review convertDtoToEntity(ReviewDto reviewDto) {
        // ReviewDto를 Review 엔티티로 변환하는 로직
    }

    private Review updateExistingReview(Review existingReview, ReviewDto reviewDto) {
        // 기존 Review 엔티티를 ReviewDto의 정보로 업데이트하는 로직
        // 업데이트 후, 저장
        return movieReviewRepository.save(existingReview);
    }
}
