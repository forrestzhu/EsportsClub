package com.jayzero.games.esportsclub.commonutils.designpattern.threadpool;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 其他Task的基类
 *
 * @author 成至
 */
public abstract class AbstractTask {

    private static final AtomicInteger ID_GENERATOR = new AtomicInteger();

    private final int id;

    public AbstractTask() {
        this.id = ID_GENERATOR.incrementAndGet();
    }

    public int getId() {
        return id;
    }

    /**
     * 执行
     */
    public abstract void execute();

    @Override
    public String toString() {
        return String.format("id=%d", id);
    }

}
