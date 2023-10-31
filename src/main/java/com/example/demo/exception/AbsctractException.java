package com.example.demo.exception;

import com.example.demo.response.ResponseStatus;

public abstract class AbsctractException extends RuntimeException {
    abstract public ResponseStatus getStatus();
    abstract public int getStatusCode();
    abstract public String getMessage();
}
