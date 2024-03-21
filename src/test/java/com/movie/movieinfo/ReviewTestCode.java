package com.movie.movieinfo;

import com.movie.movieinfo.controller.MovieReviewController;
import com.movie.movieinfo.service.movie.MovieReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;



@WebMvcTest(MovieReviewController.class)
public class ReviewTestCode {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieReviewService movieReviewService;


}
