package com.movie.movieinfo;

import com.movie.movieinfo.Repository.MovieReviewRepository;
import com.movie.movieinfo.dto.review.ReviewDto;
import com.movie.movieinfo.entity.Review;
import com.movie.movieinfo.service.movie.MovieReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ReviewServiceTestCode {

    @Autowired
    private MovieReviewService reviewService;

    @Autowired
    private MovieReviewRepository movieReviewRepository;

    @Test
    public void updateReview_ShouldUpdateReview_WhenReviewExists() {
        // Given: 존재하는 리뷰 데이터 준비
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setUserId("user1");
        reviewDto.setMovieCode("movie123");
        reviewDto.setTitle("Original Title");
        reviewDto.setDetail("Original Detail");
        reviewDto.setStarCount(5);

        // 처음에는 리뷰를 삽입
        ResponseEntity<String> insertResponse = reviewService.saveReview(reviewDto);
        assertThat(insertResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        // When: 리뷰 데이터를 업데이트
        reviewDto.setTitle("Updated Title");
        reviewDto.setDetail("Updated Detail");
        reviewDto.setStarCount(4);
        ResponseEntity<String> updateResponse = reviewService.saveReview(reviewDto);

        // Then: 업데이트가 성공적으로 이루어졌는지 확인
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Optional<Review> updatedReview = movieReviewRepository.findByUserIdAndMovieCode("user1", "movie123");
        assertThat(updatedReview.isPresent()).isTrue();
        assertThat(updatedReview.get().getTitle()).isEqualTo("Updated Title");
        assertThat(updatedReview.get().getDetail()).isEqualTo("Updated Detail");
        assertThat(updatedReview.get().getStarCount()).isEqualTo(4);
    }
}
