package com.jayzero.games.esportsclub.commonutils.designpattern.eventdriven;

/**
 * Event 事件
 *
 * @author 成至
 * @version Event.java, v0.1
 * @date 2017/09/15
 */
public interface Event {

    /**
     * 得到事件类型
     *
     * @return 类型
     */
    Class<? extends Event> getType();
}
