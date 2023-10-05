package com.zh.awe.web.exception;

import org.springframework.core.NestedRuntimeException;

import java.io.Serial;
import java.io.Serializable;

/**
 * 自定义异常类
 * @author zh 2023/7/2 15:31
 */
public class AweBaseException extends NestedRuntimeException implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public AweBaseException(String msg) {
        super(msg);
    }


}
