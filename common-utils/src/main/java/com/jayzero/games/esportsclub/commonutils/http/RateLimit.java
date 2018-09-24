package com.jayzero.games.esportsclub.commonutils.http;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * RateLimit
 *
 * @author 成至
 * @version RateLimit.java, v0.1
 * @date 2017/09/08
 */
public class RateLimit {

    /**
     * 调用频率上限
     */
    private int limit;

    /**
     * 已经使用的调用次数
     */
    private int used;

    /**
     * 剩下的可以使用的调用次数
     */
    private int remaining;

    /**
     * 还有多少秒刷新调用限制
     */
    private int resetSeconds;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }

    public int getResetSeconds() {
        return resetSeconds;
    }

    public void setResetSeconds(int resetSeconds) {
        this.resetSeconds = resetSeconds;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("limit", limit)
            .append("used", used)
            .append("remaining", remaining)
            .append("resetSeconds", resetSeconds)
            .toString();
    }
}
