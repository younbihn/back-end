package com.example.demo.exception.impl;

import com.example.demo.exception.AbsctractException;
import com.example.demo.response.ResponseStatus;
import org.springframework.http.HttpStatus;

public class CancellationOnGameDayException extends AbsctractException {
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
        return "경기 당일 혹은 그 이후에는 참여 취소가 불가능합니다.";
    }
}
