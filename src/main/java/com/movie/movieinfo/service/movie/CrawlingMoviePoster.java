package com.movie.movieinfo.service.movie;

import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class CrawlingMoviePoster {


    @Cacheable(value = "moviePosterImage", key = "#movieCd")
    public String getMoviePosterImageUrl(String movieCd) {
        String url = "https://kobis.or.kr/kobis/business/mast/mvie/searchMovieDtl.do";

        try {
            Document doc = Jsoup.connect(url)
                    .data("code", movieCd)
                    .method(Connection.Method.POST) //post 방식으로 code(= movieCd 과 동일)값으로 영화정보를 보여주고 있는 API 연동
                    .post();

            Element img = doc.select("img[src]").first();

            if (img != null) {
                String src = img.attr("src");
                String baseUrl = "https://kobis.or.kr";
                String imageUrl = src.startsWith("http") ? src : baseUrl + src;
                return imageUrl;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Poster Not Found";
    }
}
