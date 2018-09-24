package com.jayzero.games.esportsclub.commonutils.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.jayzero.games.esportsclub.commonutils.constant.DateTimeConstants;

/**
 * DateTimeDeserializer
 *
 * @author 成至
 * @version DateTimeDeserializer.java, v0.1
 * @date 2017/08/15
 */
public class DateTimeDeserializer extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        SimpleDateFormat formatter = new SimpleDateFormat(DateTimeConstants.STANDARD_DATETIME_FORMAT);
        Date date;
        try {
            date = formatter.parse(jsonParser.getText());
        } catch (ParseException e) {
            formatter = new SimpleDateFormat(DateTimeConstants.STANDARD_DATE_FORMAT);
            try {
                date = formatter.parse(jsonParser.getText());
            } catch (ParseException e1) {
                date = new Date(jsonParser.getLongValue());
            }
        }
        return date;
    }
}