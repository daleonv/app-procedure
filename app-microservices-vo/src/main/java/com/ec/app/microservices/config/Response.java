package com.ec.app.microservices.config;

import lombok.Builder;
import lombok.Getter;

/**
 * Response
 *
 * @author daleonv
 * @version 1.0
 */

@Builder
@Getter
public class Response<T> {
    private T data;
    private String message;
    private Integer code;
}

