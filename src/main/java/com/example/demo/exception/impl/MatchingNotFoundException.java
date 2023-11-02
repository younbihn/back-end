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
        return "존재하지 않는 매칭입니다.";
    }
}
