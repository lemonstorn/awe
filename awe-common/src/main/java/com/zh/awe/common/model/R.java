package com.zh.awe.common.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zh.awe.common.enums.CodeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Schema(name = "通用返回对象")
@Data
public class R<T> implements Serializable {

    private static final long serialVersionUID = 5668284352904314487L;

    @Schema(name = "状态(0成功1错误2未登陆3刷新4重复提交)")
    private Integer status;
    @Schema(name = "错误信息")
    private String msg;
    @Schema(name = "数据对象")
    private T data;
    @Schema(hidden = true)
    @JsonIgnore
    private transient Boolean ok;
    @JsonIgnore
    private transient Boolean error;

    public R() {

    }

    public R(Integer status) {
        this.status = status;
    }

    public R(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public R(T data) {
        this.data = data;
    }

    public R(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static R<String> ok() {
        return new R<>(CodeEnum.Success.getCode());
    }

    public static <E> R<E> ok(E E) {
        R<E> ajaxResult = new R<>(E);
        ajaxResult.setStatus(CodeEnum.Success.getCode());
        return ajaxResult;
    }

    public static <E> R<E> ok(String message) {
        R<E> ajaxResult = new R<>();
        ajaxResult.setStatus(CodeEnum.Success.getCode());
        ajaxResult.setMsg(message);
        return ajaxResult;
    }

    public static <E> R<E> ok(E E,String message) {
        R<E> ajaxResult = new R<>(E);
        ajaxResult.setMsg(message);
        ajaxResult.setStatus(CodeEnum.Success.getCode());
        return ajaxResult;
    }

    public static <E> R<E> error(int code, String msg) {
        R<E> ajaxResult = new R<>(code);
        ajaxResult.setMsg(msg);
        return ajaxResult;
    }

    public static <E> R<E> error(String msg) {
        R<E> ajaxResult = new R<>(CodeEnum.Fail.getCode());
        ajaxResult.setMsg(msg);
        return ajaxResult;
    }

    public static <E> R<E> error(CodeEnum code) {
        return new R<>(code.getCode(), code.getName());
    }

    public static <E> R<E> error(E E, String msg) {
        R<E> ajaxResult = new R<>(E);
        ajaxResult.setStatus(CodeEnum.Fail.getCode());
        ajaxResult.setMsg(msg);
        ajaxResult.setData(E);
        return ajaxResult;
    }

//    public static R<String> noLogin() {
//        R<String> ajaxResult = new R<>("401");
//        ajaxResult.setMsg("用户未登陆");
//        return ajaxResult;
//    }

//    public static R<String> singleLogin() {
//        R<String> ajaxResult = new R<>(WebConstants.AJAX_STATUS.noLogin);
//        ajaxResult.setMsg("该账户已经在其他地方登录");
//        return ajaxResult;
//    }

    public static R<String> notFound() {
        R<String> ajaxResult = new R<>(CodeEnum.Fail.getCode());
        ajaxResult.setMsg("资源未找到");
        return ajaxResult;
    }

//    public static R<String> isRepeat() {
//        R<String> ajaxResult = new R<>(WebConstants.AJAX_STATUS.repeat);
//        ajaxResult.setMsg("用户重复提交");
//        return ajaxResult;
//    }

    public boolean isOk() {
        if (this.status == null) {
            this.ok = false;
        }
        this.ok = CodeEnum.Success.getCode().equals(this.status);
        return ok;
    }

    public boolean isError() {
        if (this.status == null) {
            this.error = true;
        }
        this.error = CodeEnum.Success.getCode().equals(this.status);
        return error;
    }

}
