package com.zxj.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @description: 除了页面信息(question)还包含页码信息的类
 * @author: zeng
 * @date: Created in 2020/7/3 19:05
 * @modified By:
 */
@Data
public class PaginationDTO {
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;//页码
    private Integer totalPage;
    private List<Integer> pages = new ArrayList<>();
    private List<QuestionDTO> questionDTOList;

    /**
     * 该方法是根据参数获取页码信息的方法逻辑
     *
     * @param page       页码
     * @param totalPage   页码容量（一页最多显示几个）
     */
    public void setPagination(Integer totalPage, Integer page) {
        this.page = page;
        this.totalPage = totalPage;
        //页码显示思路，显示5个，页码左边2个右边2个。如果不足不显示。
        for (int i = -2; i < 3; i++) {
            if (page + i > 0 && page + i <= totalPage) {
                pages.add(page + i);
            }
        }
        //是否展示上一页,‘<’图标。仅为第一页时没有图标
        if (page == 1) {
            showPrevious = false;
        } else {
            showPrevious = true;
        }
        //是否展示下一页，'>'图标。仅为最后一页时没有图标
        if (page == totalPage) {
            showNext = false;
        } else {
            showNext = true;
        }
        //是否显示首页，'<<'。当页面含有1时，不显示
        if (pages.contains(1)) {
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }
        //是否显示尾页,">>"。当页面含有最后一页时，不显示；否则显示
        if (pages.contains(totalPage)) {
            showEndPage = false;
        } else {
            showEndPage = true;
        }
    }

    public Integer getTotalPage(Integer totalCount, Integer pageSize) {
        Integer totalPage  = 0;
        if (totalCount % pageSize == 0) {
            totalPage = totalCount / pageSize;
        } else {
            totalPage = totalCount / pageSize + 1;
        }
        return totalPage;
    }
}
