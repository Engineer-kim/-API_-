package com.movie.movieinfo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.movieinfo.controller.MovieInfoController;
import com.movie.movieinfo.dto.movie.movieList.MovieListRequestDto;
import com.movie.movieinfo.dto.movie.movieList.MovieListResponseDto;
import com.movie.movieinfo.service.movie.MovieListService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;

@WebMvcTest(controllers = MovieInfoController.class)
public class MovieControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieListService movieListService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getMovieListTest() throws Exception {
        // Given
        List<MovieListResponseDto> movieList = new ArrayList<>();
        movieList.add(new MovieListResponseDto("dasd", "dasds","ads","ads","asdsads","asd",null,null
        ));
        // 필요한 경우 MovieListResponseDto 객체를 초기화하는 코드를 추가하세요.

        MovieListRequestDto request = new MovieListRequestDto(/* 필요한 초기화 인자 */);
        // MovieListRequestDto 객체를 초기화하는 코드를 추가하세요.

        given(movieListService.getList(request)).willReturn(movieList);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/moviesList")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .param("paramName", "paramValue")) // 필요한 요청 파라미터를 추가하세요.
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(movieList)));
    }
}
