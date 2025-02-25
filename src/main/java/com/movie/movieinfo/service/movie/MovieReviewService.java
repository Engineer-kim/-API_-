package com.movie.movieinfo.service.movie;

import com.movie.movieinfo.Repository.MovieReviewRepository;
import com.movie.movieinfo.dto.review.ReviewDto;
import com.movie.movieinfo.entity.Review;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieReviewService {

    private final MovieReviewRepository movieReviewRepository;

    public ResponseEntity<String> saveReview(ReviewDto reviewDto) {
        //데이터 있는지 찿기 , 있다면 수정 없다면 인서트
        Optional<Review> existReview = movieReviewRepository.findByUserIdAndMovieCode(reviewDto.getUserId(), reviewDto.getMovieCode());
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
            return new ResponseEntity<>("Update Success", HttpStatus.OK);
        } catch (Exception e) {
            e.getMessage();
            return new ResponseEntity<>("Update Fail", HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
    //리뷰 수정하는 메서드
    private Review updateExistingReview(Review existingReview, ReviewDto reviewDto) {
        existingReview.setDetail(reviewDto.getDetail());
        existingReview.setStarCount(reviewDto.getStarCount());
        existingReview.setTitle(reviewDto.getTitle());
        return movieReviewRepository.save(existingReview);
    }


    // ReviewDto를 Review 엔티티로 변환하는 로직
    private Review convertDtoToEntity(ReviewDto reviewDto) {
        try {
            Review review = Review.builder()
                    .movieCode(reviewDto.getMovieCode())
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

    /***
     * Review 엔티티를 Review DTO 로 변환 하는 메서드
     */
    private ReviewDto convertEntityToDto(Review review) {
        return new ReviewDto(
                review.getTitle(), 
                review.getStarCount() , 
                review.getDetail() , 
                review.getUserId(), 
                review.getSeq() , 
                review.getMovieCode());
    }

    public Optional<Review> findUserReview(String userId, String movieCd) {
        Integer existReview = movieReviewRepository.countByUserIdAndMovieCode(userId, movieCd);
        if (existReview < 1) {
            return Optional.empty();
        }
        return movieReviewRepository.findByUserIdAndMovieCode(userId, movieCd);
    }
    @Transactional
    public boolean deleteReview(String userId, String movieCd) {
        Integer existReview = movieReviewRepository.countByUserIdAndMovieCode(userId, movieCd);
        if (existReview < 1) {
            //해당 회원이 쓴 리뷰가 없어서 삭제 불가
            return false;
        }
        movieReviewRepository.deleteByUserIdAndMovieCode(userId , movieCd);
        return true;
    }

    public Integer getMovieReviewCount(String movieCd) {
        Integer reviewCnt = movieReviewRepository.countByMovieCode(movieCd);
        return Objects.requireNonNullElse(reviewCnt, 0);
    }

    public List<ReviewDto> findUserReviewWithoutNoSession(String movieCd) {
        List<Review> reviews = movieReviewRepository.findByMovieCode(movieCd);
        return reviews.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }
}
