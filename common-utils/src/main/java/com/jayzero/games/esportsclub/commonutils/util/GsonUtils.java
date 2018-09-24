package com.jayzero.games.esportsclub.commonutils.util;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.common.base.Charsets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.jayzero.games.esportsclub.commonutils.exception.InternalServiceErrorException;
import com.jayzero.games.esportsclub.commonutils.exception.errorcode.UtilErrorCode;
import com.jayzero.games.esportsclub.commonutils.support.ImprovedDateTypeAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * GsonUtils
 *
 * @author 成至
 * @version GsonUtils.java, v0.1
 * @date 2017/09/15
 */
public class GsonUtils {

    private static final Logger logger = LoggerFactory.getLogger(GsonUtils.class);

    private static Gson gson = new GsonBuilder()
        .registerTypeAdapter(Date.class, new ImprovedDateTypeAdapter())
        .create();

    /**
     * 从string反序列化为object
     *
     * @param jsonString json串
     * @param type       类型, 适用于嵌套类型
     * @param <T>        泛型
     * @return type对应的Object对象
     */
    public static <T> T deserialize(String jsonString, Type type) {
        try {
            return gson.fromJson(jsonString, type);
        } catch (JsonSyntaxException e) {
            throw new InternalServiceErrorException(UtilErrorCode.GSON_DESERIALIZE_ERROR, e)
                .addErrorDescParam(jsonString);
        }
    }

    /**
     * 从string反序列化为object
     *
     * @param jsonString json串
     * @param classOfT   类型, 适用于简单类型
     * @param <T>        泛型
     * @return type对应的Object对象
     */
    public static <T> T deserialize(String jsonString, Class<T> classOfT) {
        try {
            return gson.fromJson(jsonString, classOfT);
        } catch (JsonSyntaxException e) {
            throw new InternalServiceErrorException(UtilErrorCode.GSON_DESERIALIZE_ERROR, e)
                .addErrorDescParam(jsonString);
        }
    }

    /**
     * 从byte[]反序列化为object
     *
     * @param bytes byte[]
     * @param type  类型
     * @param <T>   泛型
     * @return type对应的Object对象
     */
    public static <T> T deserialize(byte[] bytes, Type type) {
        return deserialize(new String(bytes, Charsets.UTF_8), type);
    }

    /**
     * 从byte[]反序列化为object
     *
     * @param bytes    byte[]
     * @param classOfT 类型
     * @param <T>      泛型
     * @return type对应的Object对象
     */
    public static <T> T deserialize(byte[] bytes, Class<T> classOfT) {
        return deserialize(new String(bytes, Charsets.UTF_8), classOfT);
    }

    /**
     * 序列化对象为字符串
     *
     * @param object 对象
     * @return string
     */
    public static String serialize(Object object) {
        return gson.toJson(object);
    }

    /**
     * 序列化对象为byte数组
     *
     * @param object 对象
     * @return byte数组
     */
    public static byte[] serializeAsBytes(Object object) {
        return serialize(object).getBytes(Charsets.UTF_8);
    }
}
