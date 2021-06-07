package com.usian.controller;


import com.usian.feign.ItemFeign;
import com.usian.pojo.TbItem;
import com.usian.pojo.TbItemDesc;
import com.usian.pojo.TbItemParamItem;
import com.usian.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/frontend/detail")
public class DeatilController {

    @Autowired
    private ItemFeign itemFeign;

    /**
     * 商品详情接口
     *
     * @param itemId
     * @return
     */
    @RequestMapping("/selectItemInfo")
    public Result selectItemInfo(@RequestParam(name = "itemId") Long itemId) {
        try {
            TbItem item = itemFeign.selectItemInfo(itemId);
            return Result.ok(item);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("错误");
        }
    }

    /**
     * 查询商品规格参数接口
     *
     * @param itemId
     * @return
     */
    @RequestMapping("/selectTbItemParamItemByItemId")
    public Result selectTbItemParamItemByItemId(@RequestParam("itemId") Long itemId) {
        try {
            TbItemParamItem tbItemParamItem = itemFeign.selectTbItemParamItemByItemId(itemId);
            return Result.ok(tbItemParamItem);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("查询失败");
        }
    }

    /**
     * 查询详情表
     *
     * @param itemId
     * @return
     */
    @RequestMapping("/selectItemDescByItemId")
    public Result selectItemDescByItemId(@RequestParam("itemId") Long itemId) {
        try {
            TbItemDesc itemDesc = itemFeign.selectItemDescByItemId(itemId);
            if (itemDesc != null) {
                return Result.ok(itemDesc);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("查询错误");
        }
        return Result.error("查询错误");

    }
}
