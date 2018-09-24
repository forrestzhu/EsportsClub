package com.jayzero.games.esportsclub.commonutils.support;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.jayzero.games.esportsclub.commonutils.exception.InvalidParameterException;
import com.jayzero.games.esportsclub.commonutils.exception.errorcode.UtilErrorCode;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import static com.jayzero.games.esportsclub.commonutils.constant.DateTimeConstants.ALLOWED_DATE_FORMATS;

/**
 * ImprovedDateTypeAdapter 用于gson中转换json到类, 同时支持TimeStamp以及一种本地格式
 *
 * @author 成至
 * @version ImprovedDateTypeAdapter.java, v0.1
 * @date 2017/08/07/14:56
 */
public final class ImprovedDateTypeAdapter extends TypeAdapter<Date> {

    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {

        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {

            @SuppressWarnings("unchecked")
            TypeAdapter<T> typeAdapter = (TypeAdapter<T>)((typeToken.getRawType() == Date.class)
                ? new ImprovedDateTypeAdapter()
                : null);
            return typeAdapter;
        }
    };

    private final static String STANDARD_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public ImprovedDateTypeAdapter() {
    }

    @Override
    public Date read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        return deserializeToDate(in.nextString());
    }

    private synchronized Date deserializeToDate(String json) {
        try {
            // 首先以时间戳Long格式进行解析
            return new Date(Long.parseLong(json));
        } catch (Exception e) {
            try {
                return DateUtils.parseDate(json, ALLOWED_DATE_FORMATS);
            } catch (ParseException e1) {
                throw new InvalidParameterException(UtilErrorCode.DATE_BAD_FORMAT, e1)
                    .addErrorDescParam(json);
            }
        }
    }

    @Override
    public synchronized void write(JsonWriter out, Date value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        String dateFormatAsString = DateFormatUtils.format(value, STANDARD_TIME_FORMAT);
        out.value(dateFormatAsString);
    }
}