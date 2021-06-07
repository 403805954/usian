package com.usian.service;

import com.usian.pojo.TbContent;
import com.usian.util.PageResult;

import java.util.List;

public interface TbContentService {

    PageResult selectTbContentAllByCategoryId(Long categoryId, Integer page, Integer rows);

    void insertTbContent(TbContent tbContent);

    void deleteContentByIds(Long ids);
}
