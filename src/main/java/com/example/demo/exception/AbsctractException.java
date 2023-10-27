package com.example.demo.exception;

public abstract class AbsctractException extends RuntimeException {
    abstract public int getStatusCode();
    abstract public String getMessage();
}
