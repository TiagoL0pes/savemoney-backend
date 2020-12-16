package com.savemoney.utils.handlers;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class DefaultError {

    private String dateTime;

    private HttpStatus status;

    private int code;

    private String message;

    private String path;
}
