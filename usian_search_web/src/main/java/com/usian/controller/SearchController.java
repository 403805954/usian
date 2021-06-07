package com.usian.controller;

import com.sun.org.apache.regexp.internal.RE;
import com.usian.feign.SearchFeign;
import com.usian.pojo.TbItem;
import com.usian.util.JsonUtils;
import com.usian.util.Result;
import com.usian.vo.SearchItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class SearchController {
    @Autowired
    private SearchFeign searchFeign;

    //导入数据到es接口
    @RequestMapping("/frontend/searchItem/importAll")
    public void importAll() {
        searchFeign.importAll();
    }

    //    首页搜索接口
    @RequestMapping("/frontend/searchItem/list")
    public String search(@RequestParam("q") String q) {
        List<SearchItem> searchItems = searchFeign.search(q);
        return JsonUtils.objectToJson(searchItems);

    }


}
