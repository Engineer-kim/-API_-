package com.movie.movieinfo.exception.error_400;


public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String message) {
        super(message);
    }
}
