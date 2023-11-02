package com.example.demo.exception.impl;

import com.example.demo.exception.AbsctractException;
import com.example.demo.response.ResponseStatus;
import org.springframework.http.HttpStatus;

public class YourOwnPostingCancelException extends AbsctractException {
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
        return "자신이 주최한 매칭은 참가 취소를 할 수 없습니다.";
    }
}
