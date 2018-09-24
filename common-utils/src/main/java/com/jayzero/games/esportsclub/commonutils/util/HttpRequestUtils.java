package com.jayzero.games.esportsclub.commonutils.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.ServletResponse;
import javax.servlet.ServletResponseWrapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Sets;
import com.jayzero.games.esportsclub.commonutils.exception.InternalServiceErrorException;
import com.jayzero.games.esportsclub.commonutils.exception.errorcode.UtilErrorCode;
import com.jayzero.games.esportsclub.commonutils.http.HttpMethod;
import com.jayzero.games.esportsclub.commonutils.http.bodycopier.ReusableRequestWrapper;
import com.jayzero.games.esportsclub.commonutils.http.bodycopier.ReusableResponseWrapper;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HttpRequestUtils
 *
 * @author 成至 forrestzhu.zl@alibaba-inc.com
 * @version HttpRequestUtils.java, v0.1
 * @date 2017/12/22
 */
public class HttpRequestUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class);

    private static final int DEFAULT_CONNECTION_TIMEOUT_IN_MS = 8000;

    private static final int DEFAULT_READ_TIMEOUT_IN_MS = 20000;

    private static final String UTF_8 = "UTF-8";

    private static final Set<String> IGNORED_HEADER_NAMES = Sets.newHashSet(
        "connection", "accept", "q", "x-requested-with", "user-agent",
        "accept-encoding", "accept-language");

    /**
     * 构建HttpRequest请求
     *
     * @param url        请求的url
     * @param params     参数
     * @param headers    头部信息
     * @param httpMethod 请求方法
     * @return HttpURLConnection
     */
    public static HttpURLConnection buildUrlConnection(String url, Map<String, String> params,
        Map<String, String> headers, HttpMethod httpMethod) {
        ParamValidator.validateParamNotBlank(url, "url");
        if (MapUtils.isEmpty(params)) {
            ParamValidator.validateParamNotNull(httpMethod, "httpMethod");
        }

        logger.info("buildUrlConnection for: {}", url);
        try {
            URL connectionUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection)connectionUrl.openConnection();
            setParamsForConnection(connection, params);
            setHeadersForConnection(connection, headers);
            setConnectionProperties(connection);
            if (MapUtils.isEmpty(params)) {
                connection.setRequestMethod(httpMethod.name());
            }
            return connection;
        } catch (Exception e) {
            logger.error("connection error.", e);
            throw new InternalServiceErrorException(UtilErrorCode.HTTP_REQUEST_ERROR, e);
        }
    }

    /**
     * 获得http连接的返回状态码
     *
     * @param connection HttpURLConnection
     * @return statusCode
     */
    public static int getResponseCode(HttpURLConnection connection) {
        try {
            return connection.getResponseCode();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } finally {
            connection.disconnect();
        }
    }

    /**
     * 设置连接的属性
     *
     * @param connection HttpURLConnection
     */
    private static void setConnectionProperties(HttpURLConnection connection) {
        // 设置禁止Http跳转
        HttpURLConnection.setFollowRedirects(false);
        // 设置连接超时时间和读取超时时间
        connection.setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT_IN_MS);
        connection.setReadTimeout(DEFAULT_READ_TIMEOUT_IN_MS);
    }

    /**
     * 设置请求参数
     *
     * @param connection HttpURLConnection
     * @param params     params
     */
    private static void setParamsForConnection(HttpURLConnection connection, Map<String, String> params)
        throws IOException {
        // 处理参数为空的情况
        if (connection == null || MapUtils.isEmpty(params)) {
            return;
        }

        connection.setDoOutput(true);
        DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
        dataOutputStream.writeBytes(buildParameterString(params));
        dataOutputStream.flush();
        dataOutputStream.close();
    }

    /**
     * 设置请求头
     *
     * @param connection HttpURLConnection
     * @param headers    headers
     */
    private static void setHeadersForConnection(HttpURLConnection connection, Map<String, String> headers) {
        // 处理参数为空的情况
        if (connection == null || MapUtils.isEmpty(headers)) {
            return;
        }
        headers.forEach(connection::setRequestProperty);
    }

    /**
     * 将请求的params转换为string
     *
     * @param params map类型的请求
     * @return string
     * @throws UnsupportedEncodingException encode异常
     */
    private static String buildParameterString(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder paramStringBuilder = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            paramStringBuilder.append(URLEncoder.encode(entry.getKey(), UTF_8));
            paramStringBuilder.append("=");
            paramStringBuilder.append(URLEncoder.encode(entry.getValue(), UTF_8));
            paramStringBuilder.append("&");
        }

        String paramString = paramStringBuilder.toString();
        return paramString.length() > 0
            ? paramString.substring(0, paramString.length() - 1)
            : paramString;
    }

    public static String fetchRequestParams(HttpServletRequest request) {
        StringBuilder requestParamsBuilder = new StringBuilder();
        AtomicInteger rank = new AtomicInteger();
        request.getParameterMap().forEach((key, value) -> {
            if (rank.getAndIncrement() != 0) {
                requestParamsBuilder.append(StringConstants.SEMICOLON)
                    .append(StringConstants.SPACE);
            }
            requestParamsBuilder.append(key)
                .append(StringConstants.COLON).append(StringConstants.SPACE)
                .append(String.join(StringConstants.COMMA, value));
        });

        return requestParamsBuilder.toString();
    }

    private static String fetchRequestHeaders(HttpServletRequest request) {
        StringBuilder requestHeadersBuilder = new StringBuilder();
        int rank = 0;
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();

            // 如果headerName是被ignored, 则直接忽略
            if (IGNORED_HEADER_NAMES.contains(StringUtils.lowerCase(headerName))) {
                continue;
            }

            if (rank++ != 0) {
                requestHeadersBuilder.append(StringConstants.SEMICOLON)
                    .append(StringConstants.SPACE);
            }
            requestHeadersBuilder.append(headerName)
                .append(StringConstants.COLON).append(StringConstants.SPACE)
                .append(request.getHeader(headerName));
        }

        return requestHeadersBuilder.toString();
    }

    /**
     * 从请求中获得body内容, 如果没有则返回null
     *
     * @param request 请求
     * @return body
     */
    public static String getBodyFromRequest(HttpServletRequest request) {
        if (!(request instanceof ReusableRequestWrapper)) {
            request = new ReusableRequestWrapper(request);
        }
        ReusableRequestWrapper requestWrapper = getNativeRequest(request,
            ReusableRequestWrapper.class);

        byte[] bodyBuffer = requestWrapper.getBody();
        return StringTransformUtils.byteArray2String(bodyBuffer, requestWrapper.getCharacterEncoding());
    }

    /**
     * 从response中获得body内容, 如果没有则返回null
     *
     * @param response 请求
     * @return body
     */
    public static String getBodyFromResponse(HttpServletResponse response) {
        if (!(response instanceof ReusableResponseWrapper)) {
            response = new ReusableResponseWrapper(response);
        }
        ReusableResponseWrapper responseWrapper = getNativeResponse(response,
            ReusableResponseWrapper.class);

        byte[] bodyBuffer = responseWrapper.getBody();
        return StringTransformUtils.byteArray2String(bodyBuffer, responseWrapper.getCharacterEncoding());
    }

    /**
     * Return an appropriate request object of the specified type, if available,
     * unwrapping the given request as far as necessary.
     *
     * @param request      the servlet request to introspect
     * @param requiredType the desired type of request object
     * @return the matching request object, or {@code null} if none
     * of that type is available
     */
    @SuppressWarnings("unchecked")
    public static <T> T getNativeRequest(ServletRequest request, Class<T> requiredType) {
        if (requiredType != null) {
            if (requiredType.isInstance(request)) {
                return (T)request;
            } else if (request instanceof ServletRequestWrapper) {
                return getNativeRequest(((ServletRequestWrapper)request).getRequest(), requiredType);
            }
        }
        return null;
    }

    /**
     * Return an appropriate response object of the specified type, if available,
     * unwrapping the given response as far as necessary.
     *
     * @param response     the servlet response to introspect
     * @param requiredType the desired type of response object
     * @return the matching response object, or {@code null} if none
     * of that type is available
     */
    @SuppressWarnings("unchecked")
    public static <T> T getNativeResponse(ServletResponse response, Class<T> requiredType) {
        if (requiredType != null) {
            if (requiredType.isInstance(response)) {
                return (T)response;
            } else if (response instanceof ServletResponseWrapper) {
                return getNativeResponse(((ServletResponseWrapper)response).getResponse(), requiredType);
            }
        }
        return null;
    }
}
