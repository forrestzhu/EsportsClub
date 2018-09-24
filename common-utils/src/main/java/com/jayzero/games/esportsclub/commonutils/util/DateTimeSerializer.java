package com.jayzero.games.esportsclub.commonutils.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jayzero.games.esportsclub.commonutils.constant.DateTimeConstants;

/**
 * DateTimeSerializer
 *
 * @author 成至
 * @version DateTimeSerializer.java, v0.1
 * @date 2017/08/15
 */
public class DateTimeSerializer extends JsonSerializer<Date> {

    @Override
    public void serialize(Date value, JsonGenerator jsonGenerator, SerializerProvider provider)
        throws IOException {
        SimpleDateFormat formatter = new SimpleDateFormat(DateTimeConstants.STANDARD_DATETIME_FORMAT);
        String formattedDate = formatter.format(value);
        jsonGenerator.writeString(formattedDate);
    }

}