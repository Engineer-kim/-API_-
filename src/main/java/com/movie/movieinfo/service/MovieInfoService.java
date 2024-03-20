package com.movie.movieinfo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.movieinfo.dto.MovieRequestInfoDto;
import com.movie.movieinfo.dto.MovieInfoResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
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

    public Mono<List<MovieInfoResponseDto>> getAllMovieList(MovieRequestInfoDto movieRequestInfoDto) {
        Map<String, String> dtoMap = objectMapper.convertValue(movieRequestInfoDto, Map.class);
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
                    MovieInfoResponseDto dto = MovieInfoResponseDto.builder()
                            .movieCd(movieNode.path("movieCd").asText())
                            .movieNm(movieNode.path("movieNm").asText())
                            .movieNmEn(movieNode.path("movieNmEn").asText())
                            .openDt(movieNode.path("openDt").asText())
                            .typeNm(movieNode.path("typeNm").asText())
                            .prdtStatNm(movieNode.path("prdtStatNm").asText())
                            .nationAlt(movieNode.path("nationAlt").asText())
                            .genreAlt(movieNode.path("genreAlt").asText())
                            .repNationNm(movieNode.path("repNationNm").asText())
                            .repGenreNm(movieNode.path("repGenreNm").asText())
                            .directors(movieNode.path("directors").asText())
                            .companys(movieNode.path("companys").asText())
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
