package study.likelionbeweekly.week7.exception;

import org.springframework.http.HttpStatus;

public interface BaseErrorCode {
    HttpStatus getHttpStatus();
    String getErrorMessage();
}
