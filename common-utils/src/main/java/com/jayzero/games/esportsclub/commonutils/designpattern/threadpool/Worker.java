package com.jayzero.games.esportsclub.commonutils.designpattern.threadpool;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Worker 实现 {@link Runnable} 这样可以被{@link java.util.concurrent.ExecutorService}调用
 *
 * @author 成至
 */
public class Worker implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Worker.class);

    private final AbstractTask task;

    public Worker(final AbstractTask task) {
        this.task = task;
    }

    @Override
    public void run() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        logger.info("{} start processing task[{}]", Thread.currentThread().getName(), task.toString());
        // 注意这里try语句只有finally因为不需要处理异常, 但是需要记录结束的日志
        try {
            task.execute();
        } finally {
            logger.info("{} end processing task[{}], elapsedTime:{}", Thread.currentThread().getName(),
                task.toString(), stopwatch.stop().toString());
        }
    }
}
