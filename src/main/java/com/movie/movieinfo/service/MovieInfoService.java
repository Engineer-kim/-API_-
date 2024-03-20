package com.movie.movieinfo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.movieinfo.dto.MovieInfoDto;
import com.movie.movieinfo.dto.MovieInfoResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class MovieInfoService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public MovieInfoService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.baseUrl("http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json").build();
        this.objectMapper = objectMapper;
    }

    public Mono<List<MovieInfoResponseDto>> getAllMovieList(MovieInfoDto movieInfoDto) {
        Map<String, String> dtoMap = objectMapper.convertValue(movieInfoDto, Map.class);
        MultiValueMap<String, String> multiValueParams = new LinkedMultiValueMap<>();
        dtoMap.forEach((key, value) -> {
            if (value != null) {
                multiValueParams.add(key, value);
            }
        });

        String uriString = UriComponentsBuilder.fromHttpUrl("http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json?key=1d0c83284fa09d1173eb87e683c896ee")
                .queryParams(multiValueParams)
                .toUriString();

        Mono<String> response = webClient.get()
                .uri(uriString)
                .retrieve()
                .bodyToMono(String.class);

        return response.flatMap(res -> Mono.just(parseResponseToMovieList(res)));
    }

    private List<MovieInfoResponseDto> parseResponseToMovieList(String response) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<MovieInfoResponseDto> movieInfoList = new ArrayList<>();
        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode movieList = root.path("movieListResult").path("movieList");
            if (movieList.isArray()) {
                for (JsonNode movieNode : movieList) {
                    StringBuilder directors = new StringBuilder();
                    JsonNode directorsNode = movieNode.path("directors");
                    if (directorsNode.isArray()) {
                        for (JsonNode directorNode : directorsNode) {
                            if (directors.length() > 0) directors.append(", ");
                            directors.append(directorNode.path("peopleNm").asText());
                        }
                    }
                    // 빌더 패턴을 이용한 객체 생성
                    MovieInfoResponseDto dto = MovieInfoResponseDto.builder()
                            .movieCd(movieNode.path("movieCd").asText())
                            .movieNm(movieNode.path("movieNm").asText())
                            .openDt(movieNode.path("openDt").asText())
                            .directors(directors.toString())
                            // 기타 필드들 설정...
                            .build();

                    movieInfoList.add(dto);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse the API response", e);
        }
        return movieInfoList;
    }


}
