package com.jayzero.games.esportsclub.commonutils.exception.errorcode;

import com.jayzero.games.esportsclub.commonutils.exception.ErrorCode;
import com.jayzero.games.esportsclub.commonutils.exception.ErrorCodeSupplier;
import com.jayzero.games.esportsclub.commonutils.exception.ErrorType;

import static com.jayzero.games.esportsclub.commonutils.exception.ErrorType.INTERNAL_ERROR;
import static com.jayzero.games.esportsclub.commonutils.exception.ErrorType.USER_ERROR;

/**
 * EsportsClubErrorCode
 *
 * @author 成至
 * @version EsportsClubErrorCode.java, v0.1
 * @date 2017/08/15
 */
public enum EsportsClubErrorCode implements ErrorCodeSupplier {

    /**
     * ErrorCode一共6位:
     * 形式为EsportsClub-ABC[3-7位]-123
     * ABC表示模块
     * 4,5,6位表示所属类别下的具体错误的代号
     */
    NOT_AUTHORIZED_ERROR("EsportsClub-GLOBAL-001", "缺少必要的权限", USER_ERROR),
    METHOD_NOT_IMPLEMENTED_ERROR("EsportsClub-GLOBAL-002", "方法{0}尚未实现", INTERNAL_ERROR),
    NOT_SUPPORT_ERROR("EsportsClub-GLOBAL-003", "{0}", INTERNAL_ERROR),
    PARAMETER_EMPTY("EsportsClub-GLOBAL-006", "参数[{0}]不能为空", USER_ERROR),
    INVALID_PARAMETER_ERROR("EsportsClub-GLOBAL-007", "参数错误", USER_ERROR),
    PARAMETER_SIZE_EXCEEDS_LIMIT("EsportsClub-GLOBAL-008", "参数[{0}]的数目超过最大限制数目[{1}]", USER_ERROR),
    PARAMETER_SIZE_LESS_THAN_THRESHOLD("EsportsClub-GLOBAL-009", "参数[{0}]的数目小于最少限制数目[{1}]", USER_ERROR),
    UNCATEGORIZED_ERROR("EsportsClub-GLOBAL-999", "未分类错误:{0}", INTERNAL_ERROR),
    ;

    private final ErrorCode errorCode;

    EsportsClubErrorCode(String code, String desc, ErrorType type) {
        errorCode = new ErrorCode(code, name(), desc, type);
    }

    @Override
    public ErrorCode toErrorCode() {
        return errorCode;
    }
}
