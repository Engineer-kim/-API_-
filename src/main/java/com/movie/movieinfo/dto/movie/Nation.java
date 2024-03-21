package com.movie.movieinfo.dto.movie;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Nation {
    @JsonProperty("nationNm")
    private String nationNm;
}
