package com.movie.movieinfo.service.movie;

import com.movie.movieinfo.dto.movie.movieRank.response.MovieRank;
import com.movie.movieinfo.dto.movie.movieRank.response.MovieRankResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieRankService {

    private final RestTemplate restTemplate;
    private final String baseUrl = "http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json";
    private final String key = "1d0c83284fa09d1173eb87e683c896ee";

    public List<MovieRank> getRank(LocalDate targetDate) {
        String formattedDate = targetDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String url = baseUrl + "?key=" + key+"&targetDt=" +formattedDate;
        try {
            MovieRankResponse response = restTemplate.getForObject(url, MovieRankResponse.class);
            if (response != null && response.getBoxOfficeResult() != null) {
                return response.getBoxOfficeResult().getMovieRankList();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of(); // 오류 발생 시 빈 리스트 반환
    }
}
