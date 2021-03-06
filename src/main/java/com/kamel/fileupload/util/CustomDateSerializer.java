package com.kamel.fileupload.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;


public class CustomDateSerializer extends JsonSerializer<Timestamp> {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd, MMM. yyyy hh:mm:ss");//yyyyMMddHHmmss

    @Override
    public void serialize(Timestamp timestamp, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        String formattedDate = dateFormat.format(timestamp);
        jsonGenerator.writeString(formattedDate);
    }
}
