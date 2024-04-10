package com.movie.movieinfo.service.movie;

import com.movie.movieinfo.dto.movie.movieRank.response.MovieDailyRankWrapperResponse;
import com.movie.movieinfo.dto.movie.movieRank.response.MovieWeeklyRank;
import com.movie.movieinfo.dto.movie.movieRank.response.MovieWeeklyRankWrapperResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieWeeklyRankService {

    private final RestTemplate restTemplate;
    private final String baseUrl = "http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchWeeklyBoxOfficeList.json";
    private final String key = "1d0c83284fa09d1173eb87e683c896ee";

    /**
     * 주간/주말/주중을 선택  (==>  searchCriteria)
     * “0” : 주간 (월~일)
     * “1” : 주말 (금~일) (default)
     * “2” : 주중 (월~목)
     */
    private final static String searchCriteria = "0";

    @Cacheable(value = "movieWeeklyRank", key = "#targetDate.toString()")
    public Optional<MovieWeeklyRankWrapperResponse> getWeeklyRank(LocalDate targetDate) {
        String formattedDate = targetDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String url = baseUrl + "?key=" + key + "&targetDt=" + formattedDate + "&weekGb=" + searchCriteria;
        try {
            ResponseEntity<MovieWeeklyRankWrapperResponse> responseEntity = restTemplate.exchange(
                    url, HttpMethod.GET, null, MovieWeeklyRankWrapperResponse.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.hasBody()) {
                return Optional.ofNullable(responseEntity.getBody()); //null 일수도 있으니 Optional.of 사용불가
            } else {
                return Optional.empty();
            }
        } catch (Exception e) { //예외 처리
            log.error("movieWeekly Service Logic error Occur:::::::::::::::::::::::::::::::::::" + e);
            return Optional.empty();
        }
    }
}
