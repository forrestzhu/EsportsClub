package com.jayzero.games.esportsclub.web.interceptor;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jayzero.games.esportsclub.commonutils.util.ApplicationEnvChecker;
import com.jayzero.games.esportsclub.openapi.constant.ApiConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * AuthInterceptor
 *
 * @author 成至
 * @date 2018/9/24
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Value("${spring.profiles.active}")
    private String env;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) {
        // 只鉴权开放api的接口
        if (!httpServletRequest.getRequestURI().contains(ApiConstants.API_PREFIX)) {
            logger.info("{} is not open-api, no need to check auth.", httpServletRequest.getRequestURI());
            return true;
        }
        // 非生产环境直接跳过
        if (ApplicationEnvChecker.checkIfNotProductEnv(env)) {
            logger.info(String.format("current env is [%s], no need to check auth for [%s].", env,
                httpServletRequest.getRequestURI()));
            return true;
        } else {
            return judgeIfRequestAuthorized(httpServletRequest);
        }
    }

    /**
     * 判断请求是否被授权
     *
     * @param httpServletRequest HttpServletRequest
     * @return true if authorized, false otherwise
     */
    private boolean judgeIfRequestAuthorized(HttpServletRequest httpServletRequest) {
        return false;
    }

    @PostConstruct
    public void init() {
        logger.info("spring.profiles.active = {}", env);
    }
}
