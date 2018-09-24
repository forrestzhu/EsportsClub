package com.jayzero.games.esportsclub.web;

import com.jayzero.games.esportsclub.i18n.InternationalConfig;
import com.jayzero.games.esportsclub.web.interceptor.AuthInterceptor;
import com.jayzero.games.esportsclub.web.interceptor.LogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * WebConfig
 *
 * @author 成至 forrestzhu.zl@alibaba-inc.com
 * @version WebConfig.java, v0.1
 * @date 2018/09/24
 */
@Configuration
@ComponentScan(basePackages = "com.jayzero.games.esportsclub.web")
@Import(InternationalConfig.class)
public class WebConfig implements WebMvcConfigurer {

    private final LocaleChangeInterceptor localeChangeInterceptor;

    @Autowired
    public WebConfig(LocaleChangeInterceptor localeChangeInterceptor) {
        this.localeChangeInterceptor = localeChangeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(logInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(localeChangeInterceptor).addPathPatterns("/**");
    }

    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }

    @Bean
    public LogInterceptor logInterceptor() {
        return new LogInterceptor();
    }

}