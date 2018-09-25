package com.jayzero.games.esportsclub.moba;

/**
 * Controllable 可被操控的
 *
 * @author 成至 forrestzhu.zl@alibaba-inc.com
 * @version Controllable.java, v0.1
 * @date 2018/09/25
 */
public interface Controllable {

    /**
     * 表示被某个玩家操控
     *
     * @return the Player in control of current unit
     */
    Player controlledBy();

}
