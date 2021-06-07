package com.usian.controller;

import com.usian.pojo.TbItem;
import com.usian.service.SearchService;
import com.usian.vo.SearchItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchController {
    @Autowired
    private SearchService searchService;

    @RequestMapping("/importAll")
    public void importAll() {
        searchService.importAll();
    }

    @RequestMapping("/list")
    List<SearchItem> search(String q) {
        return searchService.search(q);
    }


}
