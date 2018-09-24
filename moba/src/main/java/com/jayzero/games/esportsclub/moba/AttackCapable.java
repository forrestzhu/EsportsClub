package com.jayzero.games.esportsclub.moba;

/**
 * AttackCapable 有攻击能力的
 *
 * @author 成至 forrestzhu.zl@alibaba-inc.com
 * @version AttackCapable.java, v0.1
 * @date 2018/09/24
 */
public interface AttackCapable {

    /**
     * 对可攻击的对象进行一次攻击
     *
     * @param attackable Attackable
     */
    void attack(Attackable attackable);
}
