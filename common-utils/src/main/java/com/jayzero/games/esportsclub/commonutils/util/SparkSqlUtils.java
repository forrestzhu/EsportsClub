package com.jayzero.games.esportsclub.commonutils.util;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

/**
 * SparkSqlUtils
 *
 * @author 成至 forrestzhu.zl@alibaba-inc.com
 * @version SparkSqlUtils.java, v0.1
 * @date 2018/06/12
 */
public class SparkSqlUtils {

    /**
     * spark的执行sql的mark
     */
    private static final String SQL_MARK = "\"\"\"";

    /**
     * 生成要在shell命令中执行的sparkSql语句
     *
     * @param sparkSqlToExecute 要执行的sparkSql, sql1; sql2;等
     * @return sparkSqlInCommand
     */
    public static List<String> buildSparkSqlInCommand(String sparkSqlToExecute) {
        if (StringUtils.isBlank(sparkSqlToExecute)) {
            return Lists.newArrayList();
        } else {
            List<String> sparkSqls = SqlUtils.splitSql2List(sparkSqlToExecute);
            return sparkSqls.stream()
                .map(SparkSqlUtils::buildSingleSparkSqlInCommand)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toList());
        }
    }

    /**
     * 生成要在shell命令中执行的sparkSql语句, 传入参数是单条语句
     *
     * @param singleSparkSql 单条spark语句
     * @return singleSparkSqlInCommand
     */
    private static String buildSingleSparkSqlInCommand(String singleSparkSql) {
        singleSparkSql = StringUtils.trim(singleSparkSql);
        if (StringUtils.isNotBlank(singleSparkSql)) {
            // 如果以";"结尾, 则需要将其删除
            if (singleSparkSql.endsWith(StringConstants.SEMICOLON)) {
                singleSparkSql = singleSparkSql.substring(0, singleSparkSql.length() - 1);
            }
        }

        if (StringUtils.isNotBlank(singleSparkSql)) {
            return SQL_MARK + singleSparkSql + SQL_MARK;
        } else {
            return StringConstants.EMPTY;
        }
    }
}
