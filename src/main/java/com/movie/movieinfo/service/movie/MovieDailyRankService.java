package com.movie.movieinfo.service.movie;

import com.movie.movieinfo.dto.movie.movieRank.response.MovieDailyRank;
import com.movie.movieinfo.dto.movie.movieRank.response.MovieDailyRankWrapperResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;

@Service
@RequiredArgsConstructor
public class MovieDailyRankService {

    private final RestTemplate restTemplate;
    private final String baseUrl = "http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json";
    private final String key = "1d0c83284fa09d1173eb87e683c896ee";

    @Cacheable(value = "movieDailyRank", key = "#targetDate.toString()")
    public List<MovieDailyRank> getDailyRank(LocalDate targetDate) {
        String formattedDate = targetDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String url = baseUrl + "?key=" + key+"&targetDt=" +formattedDate;
        try {
            MovieDailyRankWrapperResponse response = restTemplate.getForObject(url, MovieDailyRankWrapperResponse.class);
            if (response != null && response.getBoxDailyOfficeResult() != null) {
                return response.getBoxDailyOfficeResult().getMovieDailyRankList();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }

    /**지역별 주간 1~10위 랭킹 로직*/
    public List<MovieDailyRank> getDailyRankWithRegionCode(LocalDate targetDate , String regionCode) {
        String formattedDate = targetDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String url = baseUrl + "?key=" + key+"&targetDt=" +formattedDate +"&comCode=" +regionCode;
        try {
            MovieDailyRankWrapperResponse response = restTemplate.getForObject(url, MovieDailyRankWrapperResponse.class);
            if (response != null && response.getBoxDailyOfficeResult() != null) {
                return response.getBoxDailyOfficeResult().getMovieDailyRankList();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }

}
