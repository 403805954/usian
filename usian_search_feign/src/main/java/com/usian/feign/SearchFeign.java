package com.usian.feign;

import com.usian.pojo.TbItem;
import com.usian.util.Result;
import com.usian.vo.SearchItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "usian-search-service")
public interface SearchFeign {

    @RequestMapping("/importAll")
    public void importAll();

    @RequestMapping("/list")
    List<SearchItem> search(@RequestParam(name = "q") String q);

}
