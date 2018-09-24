package com.jayzero.games.esportsclub.web.interceptor;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jayzero.games.esportsclub.commonutils.util.HttpRequestUtils;
import com.jayzero.games.esportsclub.commonutils.util.StringConstants;
import com.jayzero.games.esportsclub.openapi.constant.ApiConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * LogInterceptor
 *
 * @author 成至 forrestzhu.zl@alibaba-inc.com
 * @version LogInterceptor.java, v0.1
 * @date 2018/09/24
 */
public class LogInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    private ThreadLocal<String> threadLocalTrackId = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String trackId = getTrackIdFromRequestOrReturnNew(request);
        long startTime = System.currentTimeMillis();
        log(request, response, trackId, startTime);
        request.setAttribute(ApiConstants.START_TIME_KEY, startTime);
        request.setAttribute(ApiConstants.TRACK_ID_KEY, trackId);
        return true;
    }

    /**
     * 从request中取出trackId, 如果找不到trackId, 则生成一个新的trackId
     *
     * @param request request
     * @return trackId
     */
    private String getTrackIdFromRequestOrReturnNew(HttpServletRequest request) {
        if (StringUtils.isNotBlank(request.getHeader(ApiConstants.TRACK_ID_KEY))) {
            return request.getHeader(ApiConstants.TRACK_ID_KEY);
        }
        if (StringUtils.isNotBlank(request.getParameter(ApiConstants.TRACK_ID_KEY))) {
            return request.getParameter(ApiConstants.TRACK_ID_KEY);
        }
        if (StringUtils.isNotBlank((String)request.getAttribute(ApiConstants.TRACK_ID_KEY))) {
            return (String)request.getAttribute(ApiConstants.TRACK_ID_KEY);
        }
        return UUID.randomUUID().toString();
    }

    /**
     * postHandle
     */
    @Override
    public void postHandle(
        HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
        throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(
        HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String trackId = (String)request.getAttribute(ApiConstants.TRACK_ID_KEY);
        long startTime = (Long)request.getAttribute(ApiConstants.START_TIME_KEY);

        long endTime = System.currentTimeMillis();
        long elapsedTimeMilliSeconds = endTime - startTime;
        response.addHeader(ApiConstants.TRACK_ID_KEY, trackId);
        response.addHeader(ApiConstants.ELAPSED_TIME_KEY, String.valueOf(elapsedTimeMilliSeconds));

        String responseContent = HttpRequestUtils.getBodyFromResponse(response);
        logger.info("trackId {}, request elapsed time: {}ms. response status:{}. responseContent:[{}]. "
                + "Exception:[{}]. Handle:[{}]",
            trackId, elapsedTimeMilliSeconds, response.getStatus(), responseContent, ex, handler);

        // 如果需要记录到数据库中, 则在这里进行db操作

        // 删除threadLocalTrackId
        if (threadLocalTrackId != null) {
            threadLocalTrackId.remove();
        }
    }

    /**
     * 记录访问日志
     *
     * @param request   request
     * @param response  response
     * @param trackId   trackId
     * @param startTime 开始时间
     */
    private void log(HttpServletRequest request, HttpServletResponse response, String trackId, long startTime) {
        String body = HttpRequestUtils.getBodyFromRequest(request);
        String params = HttpRequestUtils.fetchRequestParams(request);

        StringBuilder message = new StringBuilder();
        message.append(String.format("trackId [%s]", trackId))
            .append(", ")
            .append(String.format("host [%s]", request.getHeader("host")))
            .append(", ")
            .append(String.format("HttpMethod [%s]", request.getMethod()))
            .append(", ")
            .append(String.format("URL [%s]", request.getRequestURI()));

        if (StringUtils.isNotBlank(params)) {
            message
                .append(", ")
                .append(StringConstants.SAFE_NEXT_LINE)
                .append(String.format("Parameters: [ %s ]", params));
        }

        if (StringUtils.isNotBlank(body)) {
            message
                .append(", ")
                .append(StringConstants.SAFE_NEXT_LINE)
                .append(String.format("Body: %s", body));
        }

        logger.info(message.toString());

        // 如果需要记录到数据库中, 则在这里进行db操作
    }
}