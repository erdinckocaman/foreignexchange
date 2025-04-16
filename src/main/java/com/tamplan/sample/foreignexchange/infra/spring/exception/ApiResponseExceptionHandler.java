package com.tamplan.sample.foreignexchange.infra.spring.exception;

import com.tamplan.sample.foreignexchange.infra.spring.response.ErrorResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiResponseExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ApiResponseExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ErrorResponseEntity handleSystemError(Exception exception, WebRequest request) {
        logger.error("System Error", exception);
        var undefinedException = new UndefinedException(exception);
        return new ErrorResponseEntity(undefinedException, undefinedException.getHttpStatus());
    }

    @ExceptionHandler(ApplicationException.class)
    public ErrorResponseEntity handleApplicationError(ApplicationException exception, WebRequest request) {
        logger.error("Application Error", exception);
        return new ErrorResponseEntity(exception, exception.getHttpStatus());
    }

}
