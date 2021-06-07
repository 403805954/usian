package com.usian.service;

import com.usian.pojo.TbContentCategory;

import java.util.List;

public interface ContentCategoryService {
    List<TbContentCategory> selectContentCategoryByParentId(Long id);

    void insertContentCategory(Long parentId, String name);

    void deleteContentCategoryById(Long categoryId);

    void updateContentCategory(Long id, String name);
}
