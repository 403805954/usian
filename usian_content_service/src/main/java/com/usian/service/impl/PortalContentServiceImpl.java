package com.usian.service.impl;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.usian.constant.ADContent;
import com.usian.mapper.TbContentMapper;
import com.usian.pojo.TbContent;
import com.usian.service.PortalContentService;
import com.usian.util.RedisClient;
import com.usian.vo.ADContentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PortalContentServiceImpl implements PortalContentService {
    @Autowired
    private RedisClient redisClient;

    @Autowired
    private TbContentMapper tbContentMapper;

    @Override
    public List<ADContentVo> selectFrontendContentByAD() {
        TbContent tbContent = new TbContent();
        tbContent.setCategoryId(ADContent.BIG_AD_CATEGORY_ID);

        List<ADContentVo> content_key = (List<ADContentVo>) redisClient.hget("CONTENT_KEY", tbContent.getCategoryId().toString());
        if (content_key != null) {
            return content_key;
        }
        List<TbContent> oldAds = tbContentMapper.select(tbContent);


        ArrayList<ADContentVo> newAds = new ArrayList<>();
        for (TbContent oldAd : oldAds) {

            ADContentVo newAd = new ADContentVo();
            newAd.setHeight(ADContent.CONTENT_HEIGHT);
            newAd.setHeightB(ADContent.CONTENT_HEIGHT_B);
            newAd.setWidth(ADContent.CONTENT_WIDTH);
            newAd.setWidthB(ADContent.CONTENT_WIDTH_B);
            newAd.setSrc(oldAd.getPic());
            newAd.setSrcB(oldAd.getPic2());
            newAd.setAlt(oldAd.getContent());
            newAd.setHref(oldAd.getUrl());
            newAds.add(newAd);
        }
        redisClient.hset("CONTENT_KEY", tbContent.getCategoryId().toString(), newAds);

        return newAds;
    }
}
