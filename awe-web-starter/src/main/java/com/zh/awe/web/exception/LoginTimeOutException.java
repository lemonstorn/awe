package com.zh.awe.web.exception;

import org.springframework.core.NestedRuntimeException;

import java.io.Serial;
import java.io.Serializable;

/**
 * 登陆超时异常类
 * @author zh 2023/7/2 15:31
 */
public class LoginTimeOutException extends NestedRuntimeException implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public LoginTimeOutException() {
        super("登录超时，请重新登录");
    }


}
