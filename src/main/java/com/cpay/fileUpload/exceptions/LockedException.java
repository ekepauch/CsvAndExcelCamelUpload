package com.cpay.fileUpload.exceptions;


public class LockedException extends AbstractException {

    public LockedException(String code, String message) {
        super(code, message);
    }
}