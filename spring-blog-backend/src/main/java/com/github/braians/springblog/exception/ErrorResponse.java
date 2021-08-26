package com.github.braians.springblog.exception;

import org.springframework.http.HttpStatus;

public class ErrorResponse extends ApiError {

    private String detail;

    public ErrorResponse(HttpStatus status, String message, String detail) {
        super(status, message);
        this.detail = detail;
    }

    public String getDetail() {
		return detail;
	}

}
