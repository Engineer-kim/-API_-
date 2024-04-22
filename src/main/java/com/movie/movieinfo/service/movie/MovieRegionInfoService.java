package com.movie.movieinfo.service.movie;

import com.movie.movieinfo.dto.movie.movieRegion.MovieRegionCode;
import com.movie.movieinfo.dto.movie.movieRegion.response.RegionCodesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieRegionInfoService {

    private final RestTemplate restTemplate;
    private final String baseUrl = "http://kobis.or.kr/kobisopenapi/webservice/rest/code/searchCodeList.json";
    private final String key = "1d0c83284fa09d1173eb87e683c896ee";

    private final String  UpperRegionCode = "0105000000"; // 바꾸면안됨 상위코드는 대한민국 지역별 고유  분류 번호임

    public Optional<MovieRegionCode> getRegionCode(String regionCode) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("key", key)
                .queryParam("comCode", UpperRegionCode);

        ResponseEntity<RegionCodesResponse> responseEntity = restTemplate.getForEntity(
                builder.toUriString(),
                RegionCodesResponse.class);

        if (responseEntity.getBody() != null) {
            return responseEntity.getBody().getCodes().stream()
                    .filter(code -> code.getFullCd().equals(regionCode))
                    .map(code -> new MovieRegionCode(code.getFullCd(), code.getKorNm()))
                    .findFirst();
        }
        return Optional.empty();
    }
}
