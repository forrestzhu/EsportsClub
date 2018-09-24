package com.jayzero.games.esportsclub.commonutils.support;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentSkipListMap;

import com.google.common.collect.Lists;

/**
 * KeyValueConfigMap
 *
 * @author 成至
 * @version KvConfigMap.java, v0.1
 * @date 2017/08/31
 */
public class KvConfigMap<K, V> {

    private Map<K, KvEntry<K, V>> configMap = new ConcurrentSkipListMap<>();

    private List<KvEntry<K, V>> configEntries = Lists.newCopyOnWriteArrayList();

    /**
     * 根据key获得value
     *
     * @param key key
     * @return value
     */
    public V get(K key) {
        return configMap.get(key).getValue();
    }

    /**
     * 根据key获得对应的entry
     *
     * @param key K
     * @return KvEntry
     */
    public KvEntry<K, V> getEntry(K key) {
        return configMap.get(key);
    }

    /**
     * 是否包含某个key
     *
     * @param key K
     * @return true if contains key, false otherwise
     */
    public boolean containsKey(K key) {
        return configMap.containsKey(key);
    }

    /**
     * getOrDefault
     *
     * @param key          K
     * @param defaultValue V, default value
     * @return returns defaultValue if get(Key) returns null, false return get(Key)'s result
     */
    public V getOrDefault(K key, V defaultValue) {
        V v;
        return (v = get(key)) == null ? defaultValue : v;
    }

    /**
     * 删除key
     *
     * @param key K
     */
    public void remove(K key) {
        configMap.remove(key);
        configEntries.removeIf(entry -> Objects.equals(entry.getKey(), key));
    }

    /**
     * 写入key, value
     *
     * @param key   K
     * @param value V
     */
    public void put(K key, V value) {
        KvEntry<K, V> entryToPut = new KvEntry<>(key, value);
        configMap.put(key, entryToPut);
        configEntries.removeIf(entry -> Objects.equals(entry.getKey(), key));
        configEntries.add(entryToPut);
    }

    /**
     * 获得所有的entries
     *
     * @return entries
     */
    public List<KvEntry<K, V>> entries() {
        return configEntries;
    }

    /**
     * 获得大小
     *
     * @return size
     */
    public int size() {
        return configEntries.size();
    }
}
