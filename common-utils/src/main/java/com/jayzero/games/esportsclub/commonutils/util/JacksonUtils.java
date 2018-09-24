package com.jayzero.games.esportsclub.commonutils.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayzero.games.esportsclub.commonutils.constant.DateTimeConstants;
import com.jayzero.games.esportsclub.commonutils.exception.InternalServiceErrorException;
import com.jayzero.games.esportsclub.commonutils.exception.errorcode.UtilErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JacksonUtils
 *
 * @author 成至
 * @version JacksonUtils.java, v0.1 2017/01/05/15:32.
 */
public class JacksonUtils {

    private static final Logger logger = LoggerFactory.getLogger(JacksonUtils.class);

    private static DateFormat dateFormat = new SimpleDateFormat(DateTimeConstants.STANDARD_DATETIME_FORMAT);

    /**
     * 遇到FAIL_ON_UNKNOWN_PROPERTIES的时候不会failed而抛出异常
     */
    private static ObjectMapper mapper = new ObjectMapper().setDateFormat(dateFormat)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    /**
     * 将string通过jackson转为对象
     *
     * @param jsonString json格式的string
     * @param tClass     TypeReference
     * @param <T>        类
     * @return T类型的对象
     */
    public static <T> T deserialize(String jsonString, TypeReference<T> tClass) {
        try {
            return mapper.readValue(jsonString, tClass);
        } catch (IOException e) {
            logger.error("Jackson string to object error, "
                + "string : {" + jsonString + "} to object type {" + tClass + "}", e);
            throw new InternalServiceErrorException(UtilErrorCode.JACKSON_DESERIALIZE_ERROR, e)
                .addErrorDescParam(jsonString);
        }
    }

    /**
     * 对象转json格式的string
     *
     * @param object 对象
     * @return jsonString
     */
    public static String serialize(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("Jackson object to string error, "
                + "object : {" + object + "} ", e);
            throw new InternalServiceErrorException(UtilErrorCode.JACKSON_SERIALIZE_ERROR, e).addErrorDescParam(object);
        }
    }

    /**
     * 对象转json格式的byte[]
     *
     * @param object 对象
     * @return byte[]
     */
    public static byte[] serializeAsBytes(Object object) {
        try {
            return mapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            logger.error("Jackson object to string error, "
                + "object : {" + object + "} ", e);
            throw new InternalServiceErrorException(UtilErrorCode.JACKSON_SERIALIZE_ERROR, e).addErrorDescParam(object);
        }
    }
}
