package com.example.demo.exception.impl;

import com.example.demo.exception.AbsctractException;
import org.springframework.http.HttpStatus;

public class S3UploadFailException extends AbsctractException {

    @Override
    public int getStatusCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    @Override
    public String getMessage() {
        return "파일 업로드가 실패했습니다.";
    }
}