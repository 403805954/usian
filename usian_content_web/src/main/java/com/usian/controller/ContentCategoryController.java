package com.usian.controller;

import com.usian.feign.ContentFeign;
import com.usian.pojo.TbContent;
import com.usian.pojo.TbContentCategory;
import com.usian.util.PageResult;
import com.usian.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/content")
public class ContentCategoryController {


    @Autowired
    private ContentFeign contentFeign;


    //内容管理	查询接口
    @RequestMapping("/selectTbContentAllByCategoryId")
    public Result selectTbContentAllByCategoryId(@RequestParam("categoryId") Long categoryId, @RequestParam(name = "page", defaultValue = "1") Integer page, @RequestParam(name = "rows", defaultValue = "20") Integer rows) {
        try {
            PageResult list = contentFeign.selectTbContentAllByCategoryId(categoryId, page, rows);
            return Result.ok(list);
        } catch (Exception e) {
            return Result.error("失败");
        }
    }

    //内容管理	添加接口
    @RequestMapping("/insertTbContent")
    public Result insertTbContent(TbContent tbContent) {
        try {
            contentFeign.insertTbContent(tbContent);
            return Result.ok();
        } catch (Exception e) {
            return Result.error("失败");
        }
    }

    //内容管理	删除接口
    @RequestMapping("/deleteContentByIds")
    public Result deleteContentByIds(@RequestParam("ids") Long ids) {
        try {
            contentFeign.deleteContentByIds(ids);
            return Result.ok();
        } catch (Exception e) {
            return Result.error("失败");
        }
    }

    //内容分类管理查询接口
    @RequestMapping("/selectContentCategoryByParentId")
    public Result selectContentCategoryByParentId(@RequestParam(name = "id", defaultValue = "0") Long id) {
        try {
            List<TbContentCategory> list = contentFeign.selectContentCategoryByParentId(id);
            return Result.ok(list);
        } catch (Exception e) {
            return Result.error("失败");
        }
    }


    //内容分类管理添加接口
    @RequestMapping("/insertContentCategory")
    public Result insertContentCategory(@RequestParam("parentId") Long parentId, @RequestParam("name") String name) {
        try {
            contentFeign.insertContentCategory(parentId, name);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("失败");
        }
    }

    //内容分类管理删除接口
    @RequestMapping("/deleteContentCategoryById")
    public Result deleteContentCategoryById(@RequestParam("categoryId") Long categoryId) {
        try {
            contentFeign.deleteContentCategoryById(categoryId);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("失败");
        }
    }

    //内容分类管理修改接口
    @RequestMapping("/updateContentCategory")
    public Result updateContentCategory(@RequestParam("id") Long id, @RequestParam("name") String name) {
        try {
            contentFeign.updateContentCategory(id, name);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("失败");
        }
    }
}
