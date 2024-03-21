package com.movie.movieinfo.exception.error_400;




public class ForbiddenException extends RuntimeException{
    public ForbiddenException(String message) {
        super(message);
    }
}
