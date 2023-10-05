package com.zh.awe.common.enums;

import java.util.Optional;

/**
 * @author 可能要删除
 */

public enum CodeEnum implements BaseEnum<CodeEnum> {
    /**
     * 整个系统通用编码 xx_xx_xxxx (服务标识_业务_错误编号，便于错误快速定位
     * 10xxx 通用错误
     * 20xxx 统一登录平台错误
     */
    Success(200, "操作成功"),
    Fail(500, "操作失败"),
    /* 参数错误：10000～19999 */
    PARAM_NOT_VALID(10001, "参数无效"),
    PARAM_IS_BLANK(10002, "参数为空"),
    PARAM_TYPE_ERROR(10003, "参数类型错误"),
    PARAM_NOT_COMPLETE(10004, "参数缺失"),
    TransferStatusError(10005, "当前状态不正确，请勿重复提交"),
    NO_PERMISSION(10006, "没有操作该功能的权限，请联系管理员授权"),
    /* 用户错误 */
    USER_NOT_LOGIN(20001, "用户未登录"),
    USER_ACCOUNT_EXPIRED(20002, "账号已过期"),
    USER_CREDENTIALS_ERROR(20003, "密码错误"),
    USER_CREDENTIALS_EXPIRED(20004, "密码过期"),
    USER_ACCOUNT_DISABLE(20005, "账号不可用"),
    USER_ACCOUNT_LOCKED(20006, "账号被锁定"),
    USER_ACCOUNT_NOT_EXIST(20007, "账号不存在"),
    USER_ACCOUNT_ALREADY_EXIST(20008, "账号已存在"),
    USER_ACCOUNT_USE_BY_OTHERS(20009, "账号下线");
    private final Integer code;
    private final String name;

    CodeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public static Optional<CodeEnum> of(Integer code) {
        if (code == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(BaseEnum.parseByCode(CodeEnum.class, code));
    }
}
