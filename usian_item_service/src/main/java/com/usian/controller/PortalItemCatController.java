package com.usian.controller;

import com.usian.service.PortalItemCatService;
import com.usian.vo.ADItemCatVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class PortalItemCatController {
    @Autowired
    private PortalItemCatService portalItemCatService;

    @RequestMapping("/itemCategory/selectItemCategoryAll")
    public ADItemCatVo selectItemCategoryAll(){
        return portalItemCatService.selectItemCategoryAll();
    }
}
