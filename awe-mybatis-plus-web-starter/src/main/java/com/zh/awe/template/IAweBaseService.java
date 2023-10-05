package com.zh.awe.template;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zh.awe.common.model.R;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zh 2023/9/17 1:02
 */
public interface IAweBaseService<E> extends IService<E> {
    /**
     * 导出
     * @param exportMenuDto
     * @param response
     */
    void export(ExportModel<E> exportMenuDto, HttpServletResponse response);

    R<String> importData(MultipartFile file, Boolean isCover);
}
