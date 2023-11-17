package com.example.demo.common;

public class ResponseUtil {
    public static <T>ResponseDto<T> SUCCESS(T response) {
        return new ResponseDto(response);
    }
}
