package com.jayzero.games.esportsclub.commonutils.util;

import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

/**
 * SqlUtils
 *
 * @author 成至 forrestzhu.zl@alibaba-inc.com
 * @version SqlUtils.java, v0.1
 * @date 2018/01/04
 */
public class SqlUtils {

    /**
     * split sql by ';' and return sql list
     *
     * @param sql sql to spilt
     * @return sql list
     * @author chengzhi
     */
    public static List<String> splitSql2List(String sql) {
        Preconditions.checkArgument(StringUtils.isNotBlank(sql), "sql is blank");

        // trim
        sql = sql.trim();
        if (!sql.endsWith(StringConstants.SEMICOLON)) {
            sql = sql + StringConstants.SEMICOLON;
        }

        List<String> splitSqls = Lists.newArrayList();

        String semSplit = ";";
        if (sql.indexOf(semSplit) == (sql.length() - 1)) {
            splitSqls.add(sql);
            return splitSqls;
        }

        Boolean isSingleQuotation = false;
        Boolean isDoubleQuotation = false;
        // 是否在反斜杠状态
        Boolean isUnderBackSlash = false;

        char[] charSql = sql.toCharArray();
        Integer beginPos = 0;
        for (int i = 0; i < charSql.length; ++i) {
            char curChar = charSql[i];
            switch (curChar) {
                case '\\':
                    isUnderBackSlash = !isUnderBackSlash;
                    break;
                case '\"':
                    if (!isUnderBackSlash) {
                        isDoubleQuotation = !isDoubleQuotation;
                    } else {
                        isUnderBackSlash = false;
                    }
                    break;
                case '\'':
                    if (!isUnderBackSlash) {
                        isSingleQuotation = !isSingleQuotation;
                    } else {
                        isUnderBackSlash = false;
                    }
                    break;
                case ';':
                    if (!isSingleQuotation && !isDoubleQuotation && !isUnderBackSlash) {
                        splitSqls.add(String.valueOf(charSql, beginPos, i - beginPos + 1));
                        beginPos = i + 1;
                    }
                    if (isUnderBackSlash) {
                        isUnderBackSlash = false;
                    }
                    break;
                default:
                    if (isUnderBackSlash) {
                        isUnderBackSlash = false;
                    }
                    break;
            }
        }
        return splitSqls;
    }
}
