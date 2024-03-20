package com.movie.movieinfo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.movieinfo.dto.MovieInfoDto;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class MovieInfoService {

    private final WebClient webClient;

    public MovieInfoService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.xml?key=dasdas").build();
    }

    public List<MovieInfoDto> getAllMovieList(Map<String, String> params) {
        MultiValueMap<String, String> multiValueParams = new LinkedMultiValueMap<>();
        params.forEach(multiValueParams::add);

        // 변환된 MultiValueMap을 사용하여 URI 생성
        String uriString = UriComponentsBuilder.fromHttpUrl("http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.xml?key=dasdas")
                .queryParams(multiValueParams)
                .toUriString();

        Mono<String> response = webClient.get()
                .uri(uriString)
                .retrieve()
                .bodyToMono(String.class);
        List<MovieInfoDto> movieList = parseResponseToMovieList(String.valueOf(response));
        return movieList;
    }

    private List<MovieInfoDto> parseResponseToMovieList(String jsonResponse) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode root = objectMapper.readTree(jsonResponse);
            JsonNode results = root.path("results");
            List<MovieInfoDto> movieList = new ArrayList<>();
            if (results.isArray()) {
                for (JsonNode result : results) {
                    MovieInfoDto movie = objectMapper.treeToValue(result, MovieInfoDto.class);
                    movieList.add(movie);
                }
            }
            return movieList;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
