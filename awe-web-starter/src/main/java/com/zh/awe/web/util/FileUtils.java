package com.zh.awe.web.util;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.zh.awe.web.exception.AweBaseException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * @author zh 2023/9/9 16:54
 */
public class FileUtils {
    public static File createJsonFile(Object jsonData) {
        File tempFile = FileUtil.createTempFile("temp", ".txt", true);
        createJsonFile(jsonData, tempFile.getAbsolutePath());
        return tempFile;
    }

    /**
     * 将JSON数据格式化并保存到文件中
     *
     * @param jsonData 需要输出的json数
     * @param filePath 输出的文件地址
     * @return 是否成功
     */
    @SneakyThrows
    public static void createJsonFile(Object jsonData, String filePath) {
        String content = JSON.toJSONString(jsonData, JSONWriter.Feature.PrettyFormat, JSONWriter.Feature.WriteMapNullValue);
        // 保证创建一个新文件
        File file = new File(filePath);
        if (!file.getParentFile().exists()) { // 如果父目录不存在，创建父目录
            boolean mkdirSuccess = file.getParentFile().mkdirs();
            if (!mkdirSuccess) {
                throw new AweBaseException("创建父目录失败");
            }
        }
        if (file.exists()) { // 如果已存在,删除旧文件
            boolean deleteSuccess = file.delete();
            if (!deleteSuccess) {
                throw new AweBaseException("删除旧文件失败");
            }
        }
        boolean createSuccess = file.createNewFile();
        if (!createSuccess) {
            throw new AweBaseException("创建新文件失败");
        }
        // 将格式化后的字符串写入文件
        FileUtil.writeString(content,file,StandardCharsets.UTF_8);
    }

    public static void downLoad(HttpServletResponse response, File file) {
        downLoad(response, file.getAbsolutePath());
    }

    /**
     * 下载文件
     * @param filePath 文件路径
     */
    public static void downLoad(HttpServletResponse response, String filePath) {
        if (filePath.contains("%")) {
            filePath = URLDecoder.decode(filePath, StandardCharsets.UTF_8);
        }

        ServletOutputStream out = null;
        FileInputStream in = null;
        try {
            in = new FileInputStream(filePath);
            String[] dir = filePath.split("/");
            String fileName = dir[dir.length - 1];
            String[] array = fileName.split("[.]");
            String fileType = array[array.length - 1].toLowerCase();
            //设置文件ContentType类型
            if ("jpg,jepg,gif,png".contains(fileType)) {//图片类型
                response.setContentType("image/" + fileType);
            } else if ("pdf".contains(fileType)) {//pdf类型
                response.setContentType("application/pdf");
            } else {//自动判断下载文件类型
                response.setContentType("multipart/form-data");
            }
            //设置文件头：最后一个参数是设置下载文件名
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
            out = response.getOutputStream();
            // 读取文件流
            int len = 0;
            byte[] buffer = new byte[1024 * 10];
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } catch (Exception ignored) {
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (Exception ignored) {
            }
        }
    }
}
