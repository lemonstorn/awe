package com.zh.awe.template;

import com.zh.awe.common.model.R;
import com.zh.awe.model.AweBaseEntity;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author zh 2023/6/22 21:50
 */
@Validated
public abstract class AweBaseController<T extends IAweBaseService<E>, E extends AweBaseEntity<E>> {
    public T serviceImpl;

    @Autowired
    public final void setServiceImpl(T serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @PostMapping("/add")
    @Operation(method = "新增", description = "公用方法")
    public R<String> add(@RequestBody E e) {
        serviceImpl.save(e);
        return R.ok();
    }

    @PostMapping("/edit")
    @Operation(method = "修改", description = "公用方法")
    public R<String> edit(@RequestBody E e) {
        serviceImpl.updateById(e);
        return R.ok();
    }

    @PostMapping("/delete")
    @Operation(method = "删除", description = "公用方法")
    public R<String> delete(@RequestBody List<String> e) {
        serviceImpl.removeBatchByIds(e);
        return R.ok();
    }

    @PostMapping("/getOne")
    public R<E> getOne(@RequestBody E e) {
        return R.ok(serviceImpl.getById(e));
    }

    @PostMapping("/export")
    public void export(@RequestBody ExportModel<E> exportMenuDto, HttpServletResponse response) {
        serviceImpl.export(exportMenuDto,response);
    }

    @PostMapping("/import")
    public R<String> importData(@RequestPart("file") MultipartFile file, Boolean isCover){
        return serviceImpl.importData(file,isCover);
    }
}


