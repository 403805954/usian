package com.usian.feign;


import com.usian.pojo.TbContent;
import com.usian.pojo.TbContentCategory;
import com.usian.util.PageResult;
import com.usian.util.Result;
import com.usian.vo.ADContentVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "usian-content-service")
public interface ContentFeign {
    //内容管理	查询接口
    @RequestMapping("/content/selectTbContentAllByCategoryId")
    public PageResult selectTbContentAllByCategoryId(@RequestParam("categoryId") Long categoryId, @RequestParam(name = "page",defaultValue = "1") Integer page, @RequestParam(name = "rows",defaultValue = "20") Integer rows);

    //内容管理	添加接口
    @RequestMapping("/content/insertTbContent")
    public void insertTbContent(@RequestBody TbContent tbContent);

    //内容管理	删除接口
    @RequestMapping("/content/deleteContentByIds")
    public void deleteContentByIds(@RequestParam("ids") Long ids);

    //首页大广告位接口
    @RequestMapping("/category/selectFrontendContentByAD")
    public List<ADContentVo> selectFrontendContentByAD();



    //内容分类管理查询接口
    @RequestMapping("/category/selectContentCategoryByParentId")
    public List<TbContentCategory> selectContentCategoryByParentId(@RequestParam("id") Long id);


    //内容分类管理添加接口
    @RequestMapping("/category/insertContentCategory")
    public void insertContentCategory(@RequestParam("parentId") Long parentId,@RequestParam("name") String name);

    //内容分类管理删除接口
    @RequestMapping("/category/deleteContentCategoryById")
    public void deleteContentCategoryById(@RequestParam("categoryId") Long categoryId);


    //内容分类管理修改接口
    @RequestMapping("/category/updateContentCategory")
    public void updateContentCategory(@RequestParam("id") Long id,@RequestParam("name") String name);
}
