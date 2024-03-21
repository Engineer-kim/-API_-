package com.movie.movieinfo.exception.error_500;

public class ServiceUnabailableException extends RuntimeException{
    public ServiceUnabailableException(String message) {
        super(message);
    }
}
