package com.jayzero.games.esportsclub.moba.player;

/**
 * PersonName
 *
 * @author 成至 forrestzhu.zl@alibaba-inc.com
 * @version PersonName.java, v0.1
 * @date 2018/10/18
 */
public class PersonName {

    /**
     * 姓: familyName, 也叫lastName
     */
    private String familyName;

    /**
     * MiddleName
     */
    private String middleName;

    /**
     * 名: givenName也叫firstName
     */
    private String givenName;

    /**
     * 命名习惯, 姓氏在前或姓氏在后
     */
    private NamingConvention namingConvention;

    /**
     * 获得用于显示的全名名字
     *
     * @return fullName to display, considering different nameConvention.
     */
    private String getFullNameForDisplay() {
        return namingConvention.showDisplayName(familyName, middleName, givenName);
    }

}
