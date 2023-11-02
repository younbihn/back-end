package com.example.demo.response;

public class ResponseUtil {

    public static <T>ResponseDto<T> SUCCESS (String message, T data) {
        return new ResponseDto(ResponseStatus.SUCCESS, message, data);
    }

}
