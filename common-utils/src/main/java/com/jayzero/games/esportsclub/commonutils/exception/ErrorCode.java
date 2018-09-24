package com.jayzero.games.esportsclub.commonutils.exception;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jayzero.games.esportsclub.commonutils.util.StringConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.requireNonNull;

/**
 * ErrorCode
 *
 * @author 成至
 * @version ErrorCode.java, v0.1
 * Date: 2017/08/07/17:05
 */
public final class ErrorCode implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(ErrorCode.class);

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY_POSTFIX = ".message";
    private static final String SOLUTION_KEY_POSTFIX = ".solution";

    private final String code;
    private final String name;
    private final String messageKey;
    private final String message;
    private final String solutionKey;
    private final String solution;
    private final ErrorType type;

    @JsonCreator
    public ErrorCode(
        @JsonProperty("code") String code,
        @JsonProperty("name") String name,
        @JsonProperty("message") String message,
        @JsonProperty("type") ErrorType type) {
        this(code, name, message, null, type);
    }

    @JsonCreator
    public ErrorCode(
        @JsonProperty("code") String code,
        @JsonProperty("name") String name,
        @JsonProperty("message") String message,
        @JsonProperty("solution") String solution,
        @JsonProperty("type") ErrorType type) {
        this.code = requireNonNull(code, "code is null");
        this.name = requireNonNull(name, "name is null");
        this.message = requireNonNull(message, "message is null");
        this.type = requireNonNull(type, "type is null");
        // 解决方案可能为空
        if (StringUtils.isBlank(solution)) {
            this.solution = StringConstants.EMPTY;
        } else {
            this.solution = solution;
        }
        this.messageKey = code + MESSAGE_KEY_POSTFIX;
        this.solutionKey = code + SOLUTION_KEY_POSTFIX;
    }

    @JsonProperty
    public String getCode() {
        return code;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public String getMessage() {
        return message;
    }

    @JsonProperty
    public String getSolution() {
        return solution;
    }

    @JsonProperty
    public ErrorType getType() {
        return type;
    }

    @JsonIgnore
    public String getMessageKey() {
        return messageKey;
    }

    @JsonIgnore
    public String getSolutionKey() {
        return solutionKey;
    }

    @Override
    public String toString() {
        return String.format("%s[%s]", message, code);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        ErrorCode that = (ErrorCode)obj;
        return Objects.equals(this.code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    /**
     * 生成消息, 格式为[code]-formattedMessage
     *
     * @param objects object, 消息
     * @return message的文本
     */
    String generateMessage(Object[] objects) {
        try {
            String formattedMessage = MessageFormat.format(message, objects);
            return String.format("[%s]-%s", code, formattedMessage);
        } catch (Exception e) {
            logger.error(String.format("Error generateMessage error. message=[%s], objects=[%s].", message,
                Arrays.toString(objects)), e);
            return message;
        }
    }
}
