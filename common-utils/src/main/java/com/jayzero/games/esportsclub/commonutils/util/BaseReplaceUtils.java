package com.jayzero.games.esportsclub.commonutils.util;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * BaseReplaceUtils
 *
 * @author 成至
 * @date 2017/9/21
 */
public class BaseReplaceUtils {

    private static final Logger logger = LoggerFactory.getLogger(BaseReplaceUtils.class);

    private static final char DOLLAR = '$';
    private static final char BEGIN_SIGN = '{';
    private static final char END_SIGN = '}';

    private static final String PREFIX = "${";
    private static final String SUFFIX = "}";
    private static final String JAVA_SCRIPT = "JavaScript";

    private static final ScriptEngineManager SCRIPT_ENGINE_MANAGER = new ScriptEngineManager();
    private static final ScriptEngine SCRIPT_ENGINE = SCRIPT_ENGINE_MANAGER.getEngineByName(JAVA_SCRIPT);

    /**
     * replaceContent
     *
     * @param content 内容
     * @param params  参数
     * @return 解析后的内容
     */
    public static String replaceContent(String content, Map<String, String> params) {
        if (StringUtils.isBlank(content) || MapUtils.isEmpty(params)) {
            return content;
        } else {
            Map<String, String> lowerCaseParams = Maps.newHashMapWithExpectedSize(params.size());
            params.forEach(
                (key, value) -> lowerCaseParams.put(StringUtils.trim(key.toLowerCase()), StringUtils.trim(value)));

            MatchStatus currentStatus = MatchStatus.NOT_MATCHED;
            StringBuilder replacedContent = new StringBuilder();

            StringBuilder totalContentInMatch = new StringBuilder();
            StringBuilder pureContentInMatch = new StringBuilder();
            char[] originContent = content.toCharArray();
            for (char singleChar : originContent) {
                currentStatus = currentStatus.transferBy(singleChar);
                switch (currentStatus) {
                    case NOT_MATCHED:
                        totalContentInMatch.append(singleChar);
                        replacedContent.append(totalContentInMatch);
                        pureContentInMatch.setLength(0);
                        totalContentInMatch.setLength(0);
                        break;
                    case DOLLAR_MATCHED:
                        totalContentInMatch.append(singleChar);
                        break;
                    case PREFIX_MATCHED:
                        totalContentInMatch.append(singleChar);
                        break;
                    case IN_CONTENT:
                        totalContentInMatch.append(singleChar);
                        pureContentInMatch.append(singleChar);
                        break;
                    case POSTFIX_MATCHED:
                        totalContentInMatch.append(singleChar);
                        String matchedKey = StringUtils.lowerCase(pureContentInMatch.toString());
                        if (StringUtils.isNotBlank(matchedKey) && lowerCaseParams.containsKey(matchedKey)) {
                            replacedContent.append(lowerCaseParams.get(matchedKey));
                        } else {
                            replacedContent.append(totalContentInMatch.toString());
                        }
                        pureContentInMatch.setLength(0);
                        totalContentInMatch.setLength(0);
                        break;
                    default:
                        throw new RuntimeException("unexpected MatchStatus.");
                }
            }
            return replacedContent.toString();
        }
    }

    public static List<String> parseDateTimeFormat(String dateTimeFormat) throws ScriptException {
        if (StringUtils.isBlank(dateTimeFormat)) {
            throw new IllegalStateException("dateTimeFormat cannot be blank!");
        }
        dateTimeFormat = dateTimeFormat.trim();

        String[] allowedChars = new String[] {"y", "M", "m", "d", "H", "s", "mi", "hh24"};
        int lastIndexOfDateTime = StringUtils.lastIndexOfAny(dateTimeFormat, allowedChars);
        // 如果没有匹配的项目
        if (lastIndexOfDateTime == -1) {
            return Collections.emptyList();
        } else {
            List<String> dateTimeFormatParsed = Lists.newArrayListWithExpectedSize(3);
            dateTimeFormatParsed.add(dateTimeFormat.substring(0, lastIndexOfDateTime + 1));
            String remaining = StringUtils.trim(dateTimeFormat.substring(lastIndexOfDateTime + 1));
            if (StringUtils.isBlank(remaining)) {
                dateTimeFormatParsed.add(StringConstants.EMPTY);
                return dateTimeFormatParsed;
            } else {
                Object value = SCRIPT_ENGINE.eval(remaining);
                dateTimeFormatParsed.add(String.valueOf(value));
                return dateTimeFormatParsed;
            }
        }
    }

    public enum MatchStatus {

        /**
         * 不匹配
         */
        NOT_MATCHED(10),

        /**
         * 匹配了$符号
         */
        DOLLAR_MATCHED(20),

        /**
         * 匹配了前缀:${
         */
        PREFIX_MATCHED(30),

        /**
         * 前缀已匹配, 正在读取内容中
         */
        IN_CONTENT(40),

        /**
         * 后缀匹配, 表明已经匹配结束
         */
        POSTFIX_MATCHED(60),
        ;

        private int code;

        MatchStatus(int code) {
            this.code = code;
        }

        public MatchStatus transferBy(char c) {
            switch (this) {
                case NOT_MATCHED:
                case POSTFIX_MATCHED:
                    if (c == DOLLAR) {
                        return DOLLAR_MATCHED;
                    } else {
                        return NOT_MATCHED;
                    }
                case DOLLAR_MATCHED:
                    if (c == BEGIN_SIGN) {
                        return PREFIX_MATCHED;
                    } else {
                        return NOT_MATCHED;
                    }
                case PREFIX_MATCHED:
                    if (c == END_SIGN) {
                        return POSTFIX_MATCHED;
                    } else {
                        return IN_CONTENT;
                    }
                case IN_CONTENT:
                    if (c == END_SIGN) {
                        return POSTFIX_MATCHED;
                    } else {
                        return IN_CONTENT;
                    }
                default:
                    throw new RuntimeException("unexpected MatchStatus.");
            }
        }
    }

}
