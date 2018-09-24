package com.jayzero.games.esportsclub.openapi.constant;

import java.util.Set;

import com.google.common.collect.Sets;

/**
 * ApiConstants
 *
 * @author 成至
 * @version ApiConstants.java, v0.1
 * @date 2018/09/14
 */
public class ApiConstants {

    /**
     * API的注意事项
     * GET  查询, 成功返回200
     * POST 创建新对象, 成功返回201 CREATED
     * PUT  按照body更新, 成功返回200
     * PATCH 按照字段更新, 成功返回200
     * DELETE 删除 成功返回204 DELETED
     *
     * field 可以用于指定需要返回的fields
     * filter 用于指定过滤条件
     */

    public static final String BASIC_API_URL = "/api";

    public static final String CURRENT_VERSION = "/v1";

    public static final String API_PREFIX = BASIC_API_URL + CURRENT_VERSION;

    public static final String RESOURCES = "/resources";

    public static final String RESOURCE_API_URL = API_PREFIX + RESOURCES;

    public static final String MEASURES = "/measures";

    public static final String MEASURE_API_URL = API_PREFIX + MEASURES;

    public static final String MONITOR = "/monitor";

    public static final String SYSTEM_CONFIG = "/admin/config";

    public static final String SYSTEM_CONFIG_URL = API_PREFIX + SYSTEM_CONFIG;

    public static final String PERFORMANCE_TEST = "/performance_test";

    public static final String PERFORMANCE_TEST_URL = API_PREFIX + PERFORMANCE_TEST;

    /**
     * 是否对结果进行封装
     */
    public static final String ENVELOP_PARAM = "envelope";

    public static final String TRUE = "true";

    public static final String FALSE = "false";

    public static final String COUNT = "count";

    public static final String TOTAL_PAGE = "total_page";

    // headers key

    public final static String TRACK_ID_KEY = "V-TrackId";

    public final static String REQUEST_UNIQUE_ID = "V-RequestUniqueId";

    public final static String START_TIME_KEY = "V-StartTime";

    public final static String FINISH_TIME_KEY = "V-FinishTime";

    public final static String ELAPSED_TIME_KEY = "V-ElapsedTime";

    /**
     * 可能会设置的response的header
     */
    public static final Set<String> RESPONSE_HEADER_PARAMS = Sets.newHashSet(COUNT, TOTAL_PAGE, TRACK_ID_KEY,
        START_TIME_KEY, FINISH_TIME_KEY, ELAPSED_TIME_KEY);

}
