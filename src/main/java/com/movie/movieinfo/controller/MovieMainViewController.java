package com.movie.movieinfo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/movieInfoMain")
public class MovieMainViewController {

    /**영화 메인*/
    @GetMapping("/main")
    public String movieMain() {
        return "movieInfoMain";
    }

    /**영화 상세*/
    @GetMapping("/detail")
    public String movieDetail(@RequestParam("movieCd")  String movieCd) {
        return "movieInfoDetail";
    }

    /**영화 검색*/
    @GetMapping("/search")
    public String movieSearch(@RequestParam("searchText")  String searchText) {return "movieInfoSearch";}

    /**지역 랭킹*/
    @GetMapping("/city")
    public String movieCity(@RequestParam("cityCode")  String cityCode) { return "movieCityRank"; }


}
