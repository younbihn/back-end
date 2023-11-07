package com.example.demo.exception.impl;

import com.example.demo.exception.AbsctractException;
import org.springframework.http.HttpStatus;

public class MatchingNotFoundException extends AbsctractException {

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }

    @Override
    public String getMessage() {
        return "해당 매칭을 찾을 수 없습니다.";
    }
}
