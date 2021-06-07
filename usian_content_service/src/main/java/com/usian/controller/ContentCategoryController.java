package com.usian.controller;

import com.usian.pojo.TbContentCategory;
import com.usian.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    //内容分类管理查询接口
    @RequestMapping("/category/selectContentCategoryByParentId")
    public List<TbContentCategory> selectContentCategoryByParentId(@RequestParam("id") Long id) {
        return contentCategoryService.selectContentCategoryByParentId(id);
    }

    //内容分类管理添加接口
    @RequestMapping("/category/insertContentCategory")
    public void insertContentCategory(@RequestParam("parentId") Long parentId, @RequestParam("name") String name) {
        contentCategoryService.insertContentCategory(parentId, name);
    }

    //内容分类管理删除接口
    @RequestMapping("/category/deleteContentCategoryById")
    public void deleteContentCategoryById(@RequestParam("categoryId") Long categoryId) {
        contentCategoryService.deleteContentCategoryById(categoryId);
    }

    //内容分类管理修改接口
    @RequestMapping("/category/updateContentCategory")
    public void updateContentCategory(@RequestParam("id") Long id, @RequestParam("name") String name) {
        contentCategoryService.updateContentCategory(id, name);
    }
}
