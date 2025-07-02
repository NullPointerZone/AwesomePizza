package com.awesomepizzasrl.client.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class RequestException extends RuntimeException{
    private final HttpStatusCode code;
    private final transient Object body;

    public RequestException(HttpStatusCode code, Object body) {
        super();
        this.code = code;
        this.body = body;
    }
}