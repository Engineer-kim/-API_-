package com.movie.movieinfo;

import com.movie.movieinfo.Repository.MovieReviewRepository;
import com.movie.movieinfo.entity.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DataJpaTest
@ActiveProfiles("test")
public class ReviewTestCode {

    @Autowired
    private MovieReviewRepository movieReviewRepository;
    @Test
    public void findByUserIdAndMovieCode_ShouldReturnReview_WhenExists() {
        // 테스트 데이터 준비
        Review review = new Review();
        review.setUserId("user1");
        review.setMovieCode("movie123");
        // 더 필요한 필드 설정
        movieReviewRepository.save(review);

        // 테스트 실행
        Optional<Review> foundReview = movieReviewRepository.findByUserIdAndMovieCode("user1", "movie123");

        // 검증
        assertThat(foundReview).isPresent();
        assertThat(foundReview.get().getUserId()).isEqualTo("user1");
        assertThat(foundReview.get().getMovieCode()).isEqualTo("movie123");
    }

    @Test
    public void deleteByUserIdAndMovieCode_ShouldDeleteReview_WhenExists() {
        // 테스트 데이터 준비
        Review review = new Review();
        review.setUserId("user2");
        review.setMovieCode("movie456");
        // 더 필요한 필드 설정
        movieReviewRepository.save(review);

        // 삭제 실행
        movieReviewRepository.deleteByUserIdAndMovieCode("user2", "movie456");

        // 삭제 확인
        Optional<Review> deletedReview = movieReviewRepository.findByUserIdAndMovieCode("user2", "movie456");
        assertThat(deletedReview).isEmpty();
    }

    @Test
    public void findByUserIdAndMovieCodeCount_ShouldReturnCount_WhenExists() {
        // 테스트 데이터 준비
        Review review = new Review();
        review.setUserId("user3");
        review.setMovieCode("movie789");
        // 더 필요한 필드 설정
        movieReviewRepository.save(review);

        // 카운트 검증
        Integer count = movieReviewRepository.countByUserIdAndMovieCode("user3", "movie789");
        assertThat(count).isEqualTo(1);
    }

    @Test
    public void deleteByUserIdAndMovieCode_ShouldThrowException_WhenNotExists() {
        // 예외 검증
        assertThrows(EmptyResultDataAccessException.class, () -> {
            movieReviewRepository.deleteByUserIdAndMovieCode("nonexistentUser", "nonexistentMovie");
        });
    }
}
