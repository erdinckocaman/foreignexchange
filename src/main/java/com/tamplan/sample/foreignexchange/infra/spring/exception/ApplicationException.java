package com.tamplan.sample.foreignexchange.infra.spring.exception;

import com.tamplan.sample.foreignexchange.domain.ErrorCode;
import com.tamplan.sample.foreignexchange.util.RandomIdGenerator;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.join;

public abstract class ApplicationException extends RuntimeException{

    private String exceptionId;
    private String code;
    private Map<String, Object> contextVars;

    protected ApplicationException(Exception innerException) {
        super(innerException);
        initException("0001");
        contextVars.put("inner_exception_msg", innerException.getMessage());
    }

    protected ApplicationException(ErrorCode errorCode, String message) {
        initException(errorCode.getCode());
        setContextVar("user_message", message);
    }

    protected ApplicationException(ErrorCode errorCode) {
        initException(errorCode.getCode());
    }

    private void initException(final String code) {
        this.code = code;
        this.exceptionId = RandomIdGenerator.generateRandomId();
        contextVars = new HashMap<>();
    }

    @Override
    public final String getMessage() {
        return exceptionId + " - " + join(",", contextVars.entrySet().stream()
                .map(entry -> {
                    var valueStr = "<null>";
                    if (entry.getValue() != null) {
                        valueStr = entry.getValue().toString();
                    }
                    return entry.getKey() + "=" + valueStr;
                })
                .map(stringEntry-> stringEntry.replace("\n", "").replace("\r", ""))
                .toList());
    }

    protected HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public boolean isUserError() {
        return HttpStatus.BAD_REQUEST == getHttpStatus();
    }

    public String getExceptionId() {
        return exceptionId;
    }

    public String getCode() {
        return code;
    }

    protected void setContextVar(String key, Object value) {
        contextVars.put(key, value);
    }

    public String getUserMessage() {
        return contextVars.getOrDefault("user_message", "").toString();
    }
}
