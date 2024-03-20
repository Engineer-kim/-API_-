package com.movie.movieinfo.dto.movie.movieList;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovieApiResponse {
    private List<Movie> movieList;

    // 기본 생성자
    public MovieApiResponse() {
    }

    // movieList의 게터와 세터
    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public static class Movie {
        private String movieCd;
        private String movieNm;
        private String movieNmEn;
        private String prdtYear;
        private String openDt;
        private String genreAlt;
        private List<Director> directors;
        private List<Company> companies;

        // 기본 생성자
        public Movie() {
        }

        // 게터와 세터
        // 여기에 필요한 게터와 세터 메소드들을 추가하시오.

    }

    // Director와 Company 클래스는 여전히 여기에 포함됩니다. 필요한 필드와 생성자, 게터, 세터를 포함합니다.
    public static class Director {
        private String peopleNm;

        public Director() {
        }

        // Director의 게터와 세터
    }

    public static class Company {
        private String companyNm;

        public Company() {
        }

        // Company의 게터와 세터
    }
}
