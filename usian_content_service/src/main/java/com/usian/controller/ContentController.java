package com.usian.controller;

import com.usian.pojo.TbContent;
import com.usian.service.TbContentService;
import com.usian.util.PageResult;
import com.usian.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/content")
public class ContentController {
    @Autowired
    private TbContentService tbContentService;

    //内容管理	查询接口
    @RequestMapping("/selectTbContentAllByCategoryId")
    public PageResult selectTbContentAllByCategoryId(@RequestParam("categoryId") Long categoryId, @RequestParam(name = "page", defaultValue = "1") Integer page, @RequestParam(name = "rows", defaultValue = "20") Integer rows) {
        return tbContentService.selectTbContentAllByCategoryId(categoryId, page, rows);
    }

    //内容管理	添加接口
    @RequestMapping("/insertTbContent")
    public void insertTbContent(@RequestBody TbContent tbContent) {
        tbContentService.insertTbContent(tbContent);
    }

    //内容管理	删除接口
    @RequestMapping("/deleteContentByIds")
    public void deleteContentByIds(@RequestParam("ids") Long ids) {
        tbContentService.deleteContentByIds(ids);
    }
}
