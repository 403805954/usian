package com.usian.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.usian.mapper.TbContentMapper;
import com.usian.pojo.TbContent;
import com.usian.service.TbContentService;
import com.usian.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TbContentServiceImpl implements TbContentService {
    @Autowired
    private TbContentMapper tbContentMapper;

    @Override
    public PageResult selectTbContentAllByCategoryId(Long categoryId, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        TbContent tbContent = new TbContent();
        tbContent.setCategoryId(categoryId);
        List<TbContent> tbContents = tbContentMapper.select(tbContent);
        PageInfo<TbContent> pageInfo = new PageInfo<>(tbContents);
        PageResult pageResult = new PageResult();
        pageResult.setPageIndex(page);
        pageResult.setTotalPage(rows);
        pageResult.setResult(pageInfo.getList());
        return pageResult;
    }

    @Override
    public void insertTbContent(TbContent tbContent) {
        tbContent.setCreated(new Date());
        tbContentMapper.insertSelective(tbContent);
    }

    @Override
    public void deleteContentByIds(Long ids) {
        tbContentMapper.deleteByPrimaryKey(ids);
    }
}
