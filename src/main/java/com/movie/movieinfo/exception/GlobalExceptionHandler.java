package com.movie.movieinfo.exception;

import com.movie.movieinfo.exception.error_400.ForbiddenException;
import com.movie.movieinfo.exception.error_400.NotFoundException;
import com.movie.movieinfo.exception.error_400.UnauthorizedException;
import com.movie.movieinfo.exception.error_500.ServiceUnabailableException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    //500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse error = new ErrorResponse();

        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage("서버(API) 통신 중 오류가 발생했습니다.");
        error.setTimestamp(System.currentTimeMillis());


        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    //503
    @ExceptionHandler(ServiceUnabailableException.class)
    public ResponseEntity<ErrorResponse> handleException(ServiceUnabailableException e) {
        ErrorResponse error = new ErrorResponse();

        error.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
        error.setMessage("너무 많은 API 호출로 과부하 상태입니다");
        error.setTimestamp(System.currentTimeMillis());


        return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
    }

    //400
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException e) {
        ErrorResponse error = new ErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage("잘못된 파라미터로 서버호출을했습니다");
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    //401
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException e) {
        ErrorResponse error = new ErrorResponse();

        error.setStatus(HttpStatus.UNAUTHORIZED.value());
        error.setMessage("미인증상태로 인증이 필요합니다.");
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }
    //403
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(ForbiddenException e) {
        ErrorResponse error = new ErrorResponse();

        error.setStatus(HttpStatus.FORBIDDEN.value());
        error.setMessage("접근 권한이 없습니다.");
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }
    //404
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(NotFoundException e) {
        ErrorResponse error = new ErrorResponse();

        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage("요청하신 엔드포인트는 없습니다(API 엔드포인트의 오타 확인)");
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


}

