package com.usian.service.impl;

import com.usian.mapper.TbItemCatMapper;
import com.usian.pojo.TbItemCat;
import com.usian.service.PortalItemCatService;
import com.usian.util.RedisClient;
import com.usian.vo.ADItemCatBVo;
import com.usian.vo.ADItemCatVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PortalItemCatServiceImpl implements PortalItemCatService {
    @Autowired
    private TbItemCatMapper tbItemCatMapper;
    @Autowired
    private RedisClient redisClient;


    @Override
    public ADItemCatVo selectItemCategoryAll() {

        ADItemCatVo key = (ADItemCatVo) redisClient.get("ITEM_CATE_KEY");

        if (key != null) {
            return key;
        }
        //查一级
        ADItemCatVo adItemCatVo = new ADItemCatVo();
        adItemCatVo.setData(getI(0L));

        redisClient.set("ITEM_CATE_KEY", adItemCatVo);
        return adItemCatVo;
    }

    public List getI(Long id) {//0
        //查一级
        TbItemCat tbItemCat1 = new TbItemCat();
        tbItemCat1.setParentId(id);
        List<TbItemCat> tbItemCats = tbItemCatMapper.select(tbItemCat1);
        List adItemCatBVos = new ArrayList<>();
        //遍历
        for (TbItemCat tbItemCat : tbItemCats) {
            if (!tbItemCat.getIsParent()) {
                adItemCatBVos.add(tbItemCat.getName());
            } else {
                ADItemCatBVo adItemCatBVo = new ADItemCatBVo();
                adItemCatBVo.setN(tbItemCat.getName());
                //查找当前分类下的子类
                List<ADItemCatBVo> list = getI(tbItemCat.getId());

                adItemCatBVo.setI(list);
                adItemCatBVos.add(adItemCatBVo);
            }
        }
        return adItemCatBVos;
    }
}
