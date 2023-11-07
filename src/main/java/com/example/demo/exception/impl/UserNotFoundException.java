package com.example.demo.exception.impl;

import com.example.demo.exception.AbsctractException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends AbsctractException {

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }

    @Override
    public String getMessage() {
        return "사용자를 찾을 수 없습니다.";
    }
}
