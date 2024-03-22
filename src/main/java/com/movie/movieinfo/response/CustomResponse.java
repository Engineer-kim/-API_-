package com.movie.movieinfo.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CustomResponse {
    private final int statusCode;
    private final String message;

    public CustomResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

}
