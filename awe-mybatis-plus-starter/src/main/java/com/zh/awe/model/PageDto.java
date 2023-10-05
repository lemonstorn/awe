package com.zh.awe.model;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * 封装分页对象
 * @author zh 2023/8/4 23:35
 */
@Data
@Schema(name = "分页对象", description = "分页对象")
public class PageDto<T> {

    /**
     * 查询数据列表
     */
    @Schema(name = "records", description = "查询数据列表")
    protected List<T> list = Collections.emptyList();

    /**
     * 总数
     */
    @Schema(name = "total", description = "总数", defaultValue = "0")
    protected long total = 0;
    /**
     * 每页显示条数，默认 10
     */
    @Schema(name = "pageSize", description = "每页显示条数，默认 10", defaultValue = "10")
    protected long pageSize = 10;

    /**
     * 当前页
     */
    @Schema(name = "pageNum", description = "当前页", defaultValue = "1")
    protected long pageNum = 1;
    /**
     * 总页数
     */
    @Schema(name = "pages", description = "总页数", defaultValue = "0")
    protected long pages = 0;

    public PageDto() {
    }

    public PageDto(IPage<T> page) {
        this.pageNum = page.getCurrent();
        this.pageSize = page.getSize();
        this.total = page.getTotal();
        this.pages = page.getPages();
        this.list = page.getRecords();
    }

    public IPage<T> toIPage() {
        IPage<T> page = new Page<>(this.pageNum, this.pageSize, this.total);
        page.setPages(this.pages);
        page.setRecords(this.list);
        return page;
    }

}
