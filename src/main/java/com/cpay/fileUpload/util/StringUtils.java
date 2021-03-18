package com.cpay.fileUpload.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Random;

public class StringUtils {
    /**
     * Replace all error message place-holders with actual values.
     */
    public static String doStringReplacements(String message, Map<String, String> attributes) {
        for (Map.Entry<String, String> value : attributes.entrySet()) {
            String key = ":" + value.getKey();
            message = message.replaceAll(key, value.getValue());
        }
        return message.replaceAll("@optional", attributes.getOrDefault("@optional",""));
    }

    public static String urlSafe(String url) {
        try {
            return URLEncoder.encode( url, "UTF-8" );
        } catch (UnsupportedEncodingException e) {
            return "Issue while encoding" +e.getMessage();
        }
    }



    public static String getSaltString() {
        String SALTCHARS = "1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 3) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();

        return saltStr;
    }


    public static String getPosId() {
        String SALTCHARS = "1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 3) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String posid = salt.toString();

        return posid;
    }






    public static String getTerminalUUID() {
        String SALTCHARS = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String posid = salt.toString();

        return posid;
    }
}
