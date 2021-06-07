package com.usian.controller;

import com.usian.feign.ItemFeign;
import com.usian.pojo.TbItemCat;
import com.usian.util.Result;
import com.usian.vo.ADItemCatBVo;
import com.usian.vo.ADItemCatVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/frontend")
public class PortalItemCatController {
    @Autowired
    private ItemFeign itemFeign;

    @RequestMapping("/itemCategory/selectItemCategoryAll")
    public Result selectItemCategoryAll(){
        try {
            ADItemCatVo list= itemFeign.selectItemCategoryAll();
            return Result.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("失败");
        }
    }
}
