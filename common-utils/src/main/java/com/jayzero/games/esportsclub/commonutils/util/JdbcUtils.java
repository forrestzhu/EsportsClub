package com.jayzero.games.esportsclub.commonutils.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JdbcUtils
 *
 * @author 成至 forrestzhu.zl@alibaba-inc.com
 * @version JdbcUtils.java, v0.1
 * @date 2018/01/26
 */
public class JdbcUtils {

    private static final Logger logger = LoggerFactory.getLogger(JdbcUtils.class);

    /**
     * 关闭连接
     *
     * @param connection Connection
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException sqlException) {
                logger.info("Could not close JDBC Connection", sqlException);
            } catch (Exception e) {
                logger.info("Unexpected exception on closing JDBC Connection", e);
            }
        }
    }

    /**
     * 关闭Statement
     *
     * @param statement Statement
     */
    public static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException sqlException) {
                logger.info("Could not close JDBC Statement", sqlException);
            } catch (Exception e) {
                logger.info("Unexpected exception on closing JDBC Statement", e);
            }
        }
    }

    /**
     * 取消一个Statement
     *
     * @param statement Statement
     */
    public static void cancelStatement(Statement statement) {
        if (statement != null) {
            try {
                if (!statement.isClosed()) {
                    statement.cancel();
                }
            } catch (SQLException sqlException) {
                logger.info("Could not cancel JDBC Statement", sqlException);
            } catch (Exception e) {
                logger.info("Unexpected exception on canceling JDBC Statement", e);
            }
        }
    }
}
