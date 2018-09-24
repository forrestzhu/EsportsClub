package com.jayzero.games.esportsclub.commonutils.exception;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jayzero.games.esportsclub.commonutils.util.StringConstants;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;

/**
 * EsportsClubException
 *
 * @author 成至
 * @version EsportsClubException.java, v0.1
 * @date 2017/08/14
 */
public class EsportsClubException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private ErrorCode errorCode;

    /**
     * 状态码,可以表示错误的大致类型, 默认是500
     */
    private int statusCode;

    /**
     * 发生错误的对象或参数名称, 是一个列表
     */
    private List<String> targets = new ArrayList<>();

    private List<String> errorDescParam = new LinkedList<>();

    private Map<String, String> contextParam = Maps.newHashMap();

    private List<Throwable> causes = new LinkedList<>();

    public EsportsClubException(ErrorCodeSupplier errorCodeSupplier) {
        this(errorCodeSupplier, 500);
    }

    public EsportsClubException(ErrorCodeSupplier errorCodeSupplier, int statusCode) {
        this(errorCodeSupplier, null, statusCode);
    }

    public EsportsClubException(ErrorCodeSupplier errorCodeSupplier, Throwable throwable) {
        this(errorCodeSupplier, throwable, 500);
    }

    public EsportsClubException(ErrorCodeSupplier errorCodeSupplier, Throwable throwable, int statusCode) {
        this.addCause(throwable);
        this.statusCode = statusCode;
        this.errorCode = errorCodeSupplier.toErrorCode();
    }

    @SuppressWarnings("unchecked")
    public <E extends EsportsClubException> E addErrorDescParam(Object desc) {
        if (desc == null) {
            this.errorDescParam.add("");
        } else if (desc instanceof String) {
            this.errorDescParam.add((String)desc);
        } else {
            try {
                this.errorDescParam.add(desc.toString());
            } catch (Exception ignored) {
                // 如果有异常, 打印出不支持toString类的类名
                this.errorDescParam.add(desc.getClass().getName());
            }
        }
        return (E)this;
    }

    @SuppressWarnings("unchecked")
    public <E extends EsportsClubException> E addContextParam(String key, Object value) {
        contextParam.put(key, value.toString());
        return (E)this;
    }

    /**
     * 增加异常信息
     *
     * @param cause 异常
     * @param <E>   泛型
     * @return EsportsClubException及其子类
     */
    @SuppressWarnings("unchecked")
    public <E extends EsportsClubException> E addCause(Throwable cause) {
        this.causes.add(cause);
        return (E)this;
    }

    /**
     * 获得contextParam的字符串形式
     *
     * @return null if contextParam is empty,
     * 一般形式为[key1:value1, key2:value2, ...]
     */
    private String getContext() {
        if (contextParam.isEmpty()) {
            return null;
        } else {
            return contextParam.entrySet().stream()
                .map(entry -> entry.getKey() + ":" + entry.getValue())
                .collect(Collectors.joining(", "));
        }
    }

    public String getMessage() {
        StringBuilder messageBuilder = new StringBuilder();
        String messagePrefix = "";

        if (StringUtils.isNotBlank(messagePrefix)) {
            messageBuilder.append(messagePrefix).append(".").append(StringConstants.SAFE_NEXT_LINE);
        }

        //加入errorCode的信息,并根据contextParam是否为空觉得是否加入context的信息
        if (contextParam.isEmpty()) {
            messageBuilder.append(errorCode.generateMessage(errorDescParam.toArray()));
        } else {
            messageBuilder
                .append(errorCode.generateMessage(errorDescParam.toArray()))
                .append(". Error Context:[ ")
                .append(getContext())
                .append(" ]");
        }

        // 增加target信息
        if (CollectionUtils.isNotEmpty(targets)) {
            messageBuilder.append(". Target:").append(targets);
        }

        if (CollectionUtils.isNotEmpty(causes)) {
            String causeMessage = causes.stream()
                .filter(Objects::nonNull)
                .map(Throwable::getMessage)
                .collect(Collectors.joining(StringConstants.SAFE_NEXT_LINE));
            if (StringUtils.isNotBlank(causeMessage)) {
                messageBuilder
                    .append(".\r\n Causes: ").append(causeMessage).append("");
            }
        }

        return messageBuilder.toString();
    }

    public List<Object> getMessageParams() {
        if (CollectionUtils.isEmpty(errorDescParam)) {
            return Lists.newArrayList();
        } else {
            return Lists.newArrayList(errorDescParam);
        }
    }

    /**
     * 返回causes
     *
     * @return causes
     */
    public String getCausesAsString() {
        StringBuilder messageBuilder = new StringBuilder();

        if (CollectionUtils.isNotEmpty(causes)) {
            String causeMessage = causes.stream()
                .filter(Objects::nonNull)
                .map(Throwable::getMessage)
                .collect(Collectors.joining(StringConstants.SAFE_NEXT_LINE));
            if (StringUtils.isNotBlank(causeMessage)) {
                messageBuilder
                    .append(".\r\n Causes: ").append(causeMessage);
            }
        }

        return messageBuilder.toString();
    }

    /**
     * 获得localizedMessage
     *
     * @param messageSource MessageSource
     * @param locale        locale信息
     * @return message
     */
    public String getLocalizedMessage(MessageSource messageSource, Locale locale) {
        StringBuilder messageBuilder = new StringBuilder();

        String pureMessage =
            messageSource.getMessage(errorCode.getMessageKey(), errorDescParam.toArray(), locale);

        //加入errorCode的信息,并根据contextParam是否为空觉得是否加入context的信息
        if (contextParam.isEmpty()) {
            messageBuilder.append(pureMessage);
        } else {
            final String errorContextKey = "esportsClub.error.context";
            String errorContext = messageSource.getMessage(errorContextKey, null, locale);
            messageBuilder
                .append(pureMessage)
                .append(". ").append(errorContext).append(":[ ")
                .append(getContext())
                .append(" ]");
        }

        return messageBuilder.toString();
    }

    /**
     * 获得localizedSolution信息
     *
     * @param messageSource MessageSource
     * @param locale        Locale
     * @return localizedSolution
     */
    public String getLocalizedSolution(MessageSource messageSource, Locale locale) {
        if (this.errorCode != null) {
            return messageSource.getMessage(this.errorCode.getSolutionKey(), null, locale);
        } else {
            final String noSolutionKey = "esportsClub.no.solution.ava";
            return messageSource.getMessage(noSolutionKey, null, locale);
        }
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public List<String> getTargets() {
        return targets;
    }

    @SuppressWarnings("unchecked")
    public <E extends EsportsClubException> E addTarget(String target) {
        if (StringUtils.isNotBlank(target) && !this.targets.contains(target)) {
            this.targets.add(target);
        }
        return (E)this;
    }

    public void setTargets(List<String> targets) {
        this.targets = targets;
    }

    public List<Throwable> getCauses() {
        return this.causes;
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
