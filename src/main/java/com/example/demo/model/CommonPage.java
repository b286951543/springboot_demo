package com.example.demo.model;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;

public class CommonPage<T> {
    private Integer pageNum; // 当前第几页
    private Integer pageSize; // 一页多少条
    private Long total; // 总条数
    private List<T> list; // 数据

    /**
     * 将PageHelper分页后的list转为分页信息
     */
    public static <T> CommonPage<T> restPage(List<T> list) {
        CommonPage<T> result = new CommonPage<T>();
        PageInfo<T> pageInfo = new PageInfo<T>(list);
        result.setPageNum(pageInfo.getPageNum());
        result.setPageSize(pageInfo.getPageSize());
        result.setTotal(pageInfo.getTotal());
        result.setList(pageInfo.getList());
        return result;
    }

    public static void setPageHelper(Integer pageNum, Integer pageSize) {
        if(pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }

        if(pageSize == null || pageSize <= 0) {
            pageSize = 10;
        }

        if(pageSize >= 20) {
            pageSize = 20;
        }

        PageHelper.startPage(pageNum, pageSize);
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
