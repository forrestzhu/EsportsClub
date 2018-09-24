package com.jayzero.games.esportsclub.commonutils.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ThreadPoolShutdownUtils 关闭线程池的工具类
 *
 * @author 成至 forrestzhu.zl@alibaba-inc.com
 * @version ThreadPoolShutdownUtils.java, v0.1
 * @date 2018/07/18
 */
public class ThreadPoolShutdownUtils {

    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolShutdownUtils.class);

    /**
     * awaitTermination的秒数
     */
    private static final long AWAIT_TERMINATION_TIMEOUT_IN_SECONDS = 1;

    /**
     * 关闭线程池
     *
     * @param executorService              ExecutorService
     * @param threadPoolName               线程池的名称, 用于记录日志
     * @param timeBeforeTakePillsInSeconds 吃药时间, 如果到了这个时间线程还没有被关闭, 就会调用shutdownNow()方法强制关闭,
     *                                     如果timeBeforeTakePillsInSeconds小于等于0, 直接强行关闭
     */
    public static void shutdownThreadPool(ExecutorService executorService, String threadPoolName,
        Integer timeBeforeTakePillsInSeconds) {
        if (executorService != null) {
            try {
                // shutdown只是不再接收新的task
                logger.info(String.format("Start shutdown threadPool: %s.", threadPoolName));
                executorService.shutdown();

                int maxLoopTimes = (int)Math.ceil(timeBeforeTakePillsInSeconds / AWAIT_TERMINATION_TIMEOUT_IN_SECONDS);
                int currentLoopTime = 0;
                boolean needTakePills = false;

                while (!executorService.awaitTermination(AWAIT_TERMINATION_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)) {
                    currentLoopTime++;
                    logger.info(String.format("Shutting down threadPool: %s for the %s time.", threadPoolName,
                        RankUtils.numberToRankText(currentLoopTime)));
                    if (currentLoopTime >= maxLoopTimes) {
                        needTakePills = true;
                        break;
                    }
                }

                if (needTakePills) {
                    shutdownThreadPoolNow(executorService, threadPoolName);
                }
                logger.info(String.format("Successfully shutdown threadPool: %s.", threadPoolName));
            } catch (Exception e) {
                logger.error(String.format("shutdown threadPool: %s error.", threadPoolName), e);
            }
        }
    }

    /**
     * 立即shutdown线程池
     *
     * @param executorService ExecutorService
     * @param threadPoolName  线程池的名称, 用于记录日志
     */
    private static void shutdownThreadPoolNow(ExecutorService executorService, String threadPoolName) {
        try {
            // shutdown只是不再接收新的task
            logger.info(String.format("Start shutdown threadPool: %s now.", threadPoolName));
            executorService.shutdownNow();

            int currentLoopTime = 0;
            while (!executorService.awaitTermination(AWAIT_TERMINATION_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)) {
                currentLoopTime++;
                logger.info(String.format("Shutting down threadPool: %s for the %s time.", threadPoolName,
                    RankUtils.numberToRankText(currentLoopTime)));
            }
            logger.info(String.format("Successfully shutdown threadPool: %s now.", threadPoolName));
        } catch (Exception e) {
            logger.error(String.format("shutdownNow threadPool: %s error.", threadPoolName), e);
        }
    }
}
