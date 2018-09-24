package com.jayzero.games.esportsclub.commonutils.util;

import java.util.concurrent.atomic.LongAdder;

/**
 * IdGenerator
 *
 * @author 成至
 * @version IdGenerator.java, v0.1
 * @date 2017/08/20
 */
public class IdGenerator {

    private LongAdder longAdder;

    public IdGenerator() {
        this.longAdder = new LongAdder();
    }

    public long generateId() {
        this.longAdder.increment();
        return this.longAdder.longValue();
    }
}
