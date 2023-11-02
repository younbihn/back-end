package com.example.demo.exception.impl;

import com.example.demo.exception.AbsctractException;
import com.example.demo.response.ResponseStatus;
import org.springframework.http.HttpStatus;

public class OverRecruitNumberException extends AbsctractException {
    @Override
    public ResponseStatus getStatus() {
        return ResponseStatus.ERROR;
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "모집 인원보다 많은 인원을 수락할 수 없습니다.";
    }
}
