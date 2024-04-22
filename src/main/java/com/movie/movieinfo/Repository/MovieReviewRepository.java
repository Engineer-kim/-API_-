package com.movie.movieinfo.Repository;

import com.movie.movieinfo.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieReviewRepository extends JpaRepository<Review, Integer> {
    Optional<Review> findByUserIdAndMovieCode(String userId, String movieCode);
    void deleteByUserIdAndMovieCode(String userId, String movieCode);

    Integer countByUserIdAndMovieCode(String userId, String movieCode);

    Integer getMovieReviewCount(String movieCd);
}
