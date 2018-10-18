package com.jayzero.games.esportsclub.moba.player;

/**
 * NamingConvention
 *
 * @author 成至 forrestzhu.zl@alibaba-inc.com
 * @version NamingConvention.java, v0.1
 * @date 2018/10/18
 */
public enum NamingConvention {

    /**
     * 中国式: 姓在前,名在后
     */
    CHINESE,

    /**
     * 西方式: 名在前, 姓在后
     */
    WESTERN;

    /**
     * 显示displayName
     *
     * @param familyName 姓
     * @param middleName middleName
     * @param givenName  名
     * @return displayName
     */
    public String showDisplayName(String familyName, String middleName, String givenName) {
        StringBuilder nameBuilder = new StringBuilder();
        switch (this) {
            case CHINESE:
                nameBuilder.append(familyName).append(middleName).append(givenName);
                break;
            case WESTERN:
                nameBuilder.append(givenName).append(middleName).append(familyName);
                break;
            default:
                throw new IllegalStateException(String.format("no such nameConvention: %s.", this.name()));
        }
        return nameBuilder.toString();
    }
}
