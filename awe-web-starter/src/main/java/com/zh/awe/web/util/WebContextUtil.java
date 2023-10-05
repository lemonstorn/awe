package com.zh.awe.web.util;

import com.zh.awe.common.utils.JsonUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * web工具类
 * @author zh 2023/7/8 4:31
 */
@Slf4j
public class WebContextUtil {
    /**
     * 将返回数据用json格式输出
     */
    public static void responseOutWithJson(HttpServletResponse response, Object object) throws IOException {
        OutputStream out = null;
        try {
            String responseJSONObject = JsonUtils.objectToJson(object);
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-type", "application/json;charset=UTF-8");
            out = response.getOutputStream();
            assert responseJSONObject != null;
            out.write(responseJSONObject.getBytes(StandardCharsets.UTF_8));
            out.flush();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * 判断是否是ajax请求
     * @param request 请求
     * @return   true:是ajax请求 false:不是ajax请求
     */
    public static boolean isAjax(HttpServletRequest request) {
        return (request.getHeader("accept") != null && (request.getHeader("accept").contains("application/json")) ||
                request.getHeader("content-type") != null && (request.getHeader("content-type").contains("application/json")))
                || (request.getHeader("X-Requested-With") != null
                && request.getHeader("X-Requested-With").contains("XMLHttpRequest"));
    }
}
