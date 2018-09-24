package com.jayzero.games.esportsclub.commonutils.designpattern.eventdriven;

import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;

/**
 * Handler 事件处理interface
 *
 * @author 成至
 * @version Handler.java, v0.1
 * @date 2017/09/15
 */
public interface Handler<E extends Event> {

    /**
     * 处理事件
     *
     * @param event 要被处理的事件
     * @throws Exception 出现异常时抛出
     */
    void handleEvent(E event) throws Exception;

    /**
     * 默认的批量实现是一个一个的调用handleEvent接口
     *
     * @param events 要被处理的事件列表
     * @throws Exception 出现异常时抛出
     */
    default void handleEvents(Collection<E> events) throws Exception {
        if (CollectionUtils.isNotEmpty(events)) {
            for (E event : events) {
                handleEvent(event);
            }
        }
    }
}
