package com.jayzero.games.esportsclub.commonutils.support;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * KvEntry
 *
 * @author 成至 forrestzhu.zl@alibaba-inc.com
 * @version KvEntry.java, v0.1
 * @date 2018/04/28
 */
public class KvEntry<K, V> {

    /**
     * key
     */
    private K key;

    /**
     * value
     */
    private V value;

    public KvEntry() {
    }

    public KvEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("key", key)
            .append("value", value)
            .toString();
    }
}
