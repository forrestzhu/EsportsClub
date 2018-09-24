package com.jayzero.games.esportsclub.commonutils.util;

/**
 * StorageUnit
 *
 * @author 成至 forrestzhu.zl@alibaba-inc.com
 * @version StorageUnit.java, v0.1
 * @date 2018/06/15
 */
public enum StorageUnit {

    /**
     * unit in bytes
     */
    B(0),

    /**
     * unit in kilobytes
     */
    KB(1),

    /**
     * unit in megabytes
     */
    MB(2),

    /**
     * unit in gigabytes
     */
    GB(3),

    /**
     * unit in terabytes
     */
    TB(4),

    /**
     * unit in petabytes
     */
    PB(5);

    /**
     * 表示是1024的多少次方
     */
    private int powerOf1024;

    StorageUnit(int powerOf1024) {
        this.powerOf1024 = powerOf1024;
    }

    public int getPowerOf1024() {
        return powerOf1024;
    }

    /**
     * 根据Name获取枚举实例
     *
     * @param storageUnit storageUnit
     * @return StorageUnit
     */
    public static StorageUnit fromName(String storageUnit) throws IllegalArgumentException {
        for (StorageUnit value : StorageUnit.values()) {
            if (value.name().equalsIgnoreCase(storageUnit)) {
                return value;
            }
        }
        throw new IllegalArgumentException(String.format("unknown name %s for StorageUnit", storageUnit));
    }
}
