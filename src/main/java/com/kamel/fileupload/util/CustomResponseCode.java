package com.kamel.fileupload.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

public class CustomResponseCode {

    public static final String DEFAULT = "0";
    public static final String FAILED ="101";
    public static final String BAD_REQUEST = "400";
    public static final String CONFLICT_EXCEPTION = "299";
    public static final String LOCKED_EXCEPTION = "423";
    public static final String NOT_FOUND_EXCEPTION = "404";
    public static final String SUCCESS = "200";
    public static final int ACTIVE_USER = 1;
    public static final int INACTIVE_USER = 0;
    public static final int DEACTIVE_USER = 2;
    public static final String CHANGE_P_REQUIRED = "15";
    public static final String UNAUTHORIZED = "401";
    public static final String SEE_OTHER = "303";
    public static final int ENABLE = 1;
    public static final int DISABLE = 0;
    public static final int DB_SUCCESS = 1;
    public static final String FOUND = "302";
    public static final String CREATED = "201";
    public static final String ACCEPTED = "202";
    public static final String NO_CONTENT = "204";
    public static final String FORMAT_NOT_ALLOWED = "205";
    public static final String FORBIDDEN = "403";
    public static final String INTERNAL_SERVER_ERROR = "500";
    public static final String NOT_MODIFIED = "304";
    public static final String TEMPORARY_REDIRECT = "307";
    public static final String METHOD_NOT_ALLOWED = "405";
    public static final String NOT_ACCEPTABLE = "406";
    public static final String UNSUPPORTED_MEDIA_TYPE = "415";
    public static final String NOT_IMPLEMENTED = "501";
    public static final String SERVICE_UNAVAILABLE = "04";




    public static final String VALIDATION_MESSAGE = "Input Validation failed";
    private static final Logger logger = LoggerFactory.getLogger(CustomResponseCode.class);
    private static final Map<String, String> httpCodes = new HashMap<>();

    public static String getMessage(String code) {
        String message = "Undefined";
        try {
            message = httpCodes.get(code);
        } catch (Exception e) {
            logger.error("Error in getMessage {} :", "cannot get message from code", e);
        }
        return message;
    }



    @PostConstruct
    public void init() {
        httpCodes.put(CREATED, "Resource is Created");
        httpCodes.put(ACCEPTED, "Accepted");
        httpCodes.put(NO_CONTENT, "No Content");
        httpCodes.put(BAD_REQUEST, "Bad Request");
        httpCodes.put(UNAUTHORIZED, "Unauthorized");
        httpCodes.put(FORBIDDEN, "Forbidden");
        httpCodes.put(INTERNAL_SERVER_ERROR, "Internal Server Error");
        httpCodes.put(NOT_FOUND_EXCEPTION, "Not Found");
        httpCodes.put(SUCCESS, "Successful");
        httpCodes.put(CONFLICT_EXCEPTION, "Conflict Exception");
        httpCodes.put(FOUND, "Found");
        httpCodes.put(SEE_OTHER, "See Other");
        httpCodes.put(NOT_MODIFIED, "Not Modified");
        httpCodes.put(TEMPORARY_REDIRECT, "Temporary Redirect");
        httpCodes.put(METHOD_NOT_ALLOWED, "Method Not Allowed");
        httpCodes.put(NOT_ACCEPTABLE, "Method Not Allowed");
        httpCodes.put(UNSUPPORTED_MEDIA_TYPE, "Unsupported Media Type");
        httpCodes.put(NOT_IMPLEMENTED, "Not Implemented");
    }





}
