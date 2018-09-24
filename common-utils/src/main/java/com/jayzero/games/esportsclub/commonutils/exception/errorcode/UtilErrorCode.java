package com.jayzero.games.esportsclub.commonutils.exception.errorcode;

import com.jayzero.games.esportsclub.commonutils.exception.ErrorCode;
import com.jayzero.games.esportsclub.commonutils.exception.ErrorCodeSupplier;
import com.jayzero.games.esportsclub.commonutils.exception.ErrorType;

import static com.jayzero.games.esportsclub.commonutils.exception.ErrorType.INTERNAL_ERROR;
import static com.jayzero.games.esportsclub.commonutils.exception.ErrorType.USER_ERROR;

/**
 * UtilErrorCode
 *
 * @author 成至
 * @version UtilErrorCode.java, v0.1
 * @date 2017/08/18
 */
public enum UtilErrorCode implements ErrorCodeSupplier {

    /**
     * ErrorCode一共6位:
     * 形式为ECLUB-ABC-123
     * ABC表示模块
     * 4,5,6位表示所属类别下的具体错误的代号
     */
    CRON_EXPRESSION_PARSE_ERROR("ECLUB-UTIL-001", "cron表达式{0}解析错误", "请填写正确的cron表达式, 参见:https://crontab.guru",
        USER_ERROR),
    DATE_BAD_FORMAT("ECLUB-UTIL-002", "日期[{0}]格式错误",
        "请填写正确的日期格式, 目前支持的日期格式有: [yyyyMMdd, yyyy-MM-dd, yyyy-MM-dd HH:mm:ss]",
        USER_ERROR),

    UNABLE_TO_CONVERT_AS_HTTP_PARAM("ECLUB-UTIL-005", "无法将对象转换为HttpParams, 对象为:[{0}]", "", USER_ERROR),

    GSON_SERIALIZE_ERROR("ECLUB-UTIL-011", "使用Gson序列化时发生错误, object_to_serialize:[{0}]", "", INTERNAL_ERROR),
    GSON_DESERIALIZE_ERROR("ECLUB-UTIL-012", "使用Gson反序列化时发生错误, string_to_deserialize:[{0}]", "", INTERNAL_ERROR),
    JACKSON_SERIALIZE_ERROR("ECLUB-UTIL-015", "使用Jackson序列化时发生错误, object_to_serialize:[{0}]", "", INTERNAL_ERROR),
    JACKSON_DESERIALIZE_ERROR("ECLUB-UTIL-016", "使用Jackson反序列化时发生错误, string_to_deserialize:[{0}]", "",
        INTERNAL_ERROR),

    MERGE_FILE_ERROR_NO_TARGET_FILE("ECLUB-UTIL-031",
        "将多个文件的内容合并到同一个文件时出错, 目标文件未指定",
        "",
        USER_ERROR),
    MERGE_FILE_ERROR_NO_SOURCE_FILE("ECLUB-UTIL-033", "将多个文件的内容合并到同一个文件时出错, 没有可用的源文件",
        "请确认在合并文件的时候源文件是存在的", USER_ERROR),

    HTTP_REQUEST_ERROR("ECLUB-UTIL-041", "构建或发送Http请求的时候出错",
        "eclub内部错误, 请联系eclub开发人员解决", INTERNAL_ERROR),
    LOCALE_ERROR("ECLUB-UTIL-050", "local", "locale", INTERNAL_ERROR),
    ;

    private final ErrorCode errorCode;

    UtilErrorCode(String code, String message, String solution, ErrorType type) {
        errorCode = new ErrorCode(code, name(), message, solution, type);
    }

    UtilErrorCode(String code, String message, ErrorType type) {
        errorCode = new ErrorCode(code, name(), message, type);
    }

    @Override
    public ErrorCode toErrorCode() {
        return errorCode;
    }
}
