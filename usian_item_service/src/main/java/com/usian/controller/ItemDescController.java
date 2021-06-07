package com.usian.controller;

import com.usian.pojo.TbItemDesc;
import com.usian.service.ItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/itemDesc")
public class ItemDescController {
    @Autowired
    private ItemDescService itemDescService;

    @RequestMapping("/selectItemDescByItemId")
    public TbItemDesc selectItemDescByItemId(@RequestParam("itemId") Long itemId){
        return itemDescService.selectItemDescByItemId(itemId);
    }


}
