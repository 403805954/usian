package com.usian.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.usian.mapper.*;
import com.usian.pojo.*;
import com.usian.service.ItemService;
import com.usian.util.IDUtils;
import com.usian.util.PageResult;
import com.usian.util.Result;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service

public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper tbItemMapper;

    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    @Autowired
    private TbItemParamMapper tbItemParamMapper;

    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;


    @Override
    public List<TbItemCat> selectItemCategoryByParentId(Long id) {
        TbItemCat tbItemCat = new TbItemCat();
        tbItemCat.setParentId(id);
        return tbItemCatMapper.select(tbItemCat);
    }

    /**
     * 分页查询
     *
     * @param page
     * @param rows
     * @return
     */
    @Override
    public PageResult selectTbItemAllByPage(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<TbItem> list = tbItemMapper.selectAll();
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        PageResult pageResult = new PageResult();
        pageResult.setPageIndex(page);
        pageResult.setResult(pageInfo.getList());
        pageResult.setTotalPage(rows);
        return pageResult;
    }

    /**
     * @param itemCatId
     * @return
     */
    @Override
    public TbItemParam selectItemParamByItemCatId(Long itemCatId) {
        TbItemParam tbItemParam = new TbItemParam();
        tbItemParam.setItemCatId(itemCatId);
        return tbItemParamMapper.selectOne(tbItemParam);
    }

    @Override
    @Transactional
    public void insertTbItem(ItemVo itemVo) {
        //itemVo
        Date date = new Date();
        long id = IDUtils.genItemId();
        itemVo.setId(id);
        itemVo.setCreated(date);
        itemVo.setStatus((byte) 1);

        tbItemMapper.insertSelective(itemVo);


        //desc
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemId(id);
        tbItemDesc.setCreated(date);
        tbItemDesc.setItemDesc(itemVo.getDesc());
        tbItemDescMapper.insertSelective(tbItemDesc);

        //item_param_item
        TbItemParamItem tbItemParamItem = new TbItemParamItem();
        tbItemParamItem.setItemId(id);
        tbItemParamItem.setCreated(date);
        tbItemParamItem.setParamData(itemVo.getItemParams());
        tbItemParamItemMapper.insertSelective(tbItemParamItem);
    }

    @Override
    public void deleteItemById(Long itemId) {
        /*tbItemMapper.updateStatus(itemId);*/
        tbItemMapper.deleteByPrimaryKey(itemId);
        tbItemDescMapper.deleteByPrimaryKey(itemId);
        tbItemParamItemMapper.deleteByItemId(itemId);
    }

    @Override
    public Map<String, Object> preUpdateItem(Long itemId) {
        Map<String, Object> map = new HashMap<>();
        //根据商品 ID 查询商品
        TbItem item = this.tbItemMapper.selectByPrimaryKey(itemId);
        map.put("item", item);
        //根据商品 ID 查询商品描述
        TbItemDesc itemDesc = this.tbItemDescMapper.selectByPrimaryKey(itemId);
        map.put("itemDesc", itemDesc.getItemDesc());
        //根据商品 ID 查询商品类目
        TbItemCat itemCat = this.tbItemCatMapper.selectByPrimaryKey(item.getCid());
        if (itemCat != null) {
            map.put("itemCat", itemCat.getName());
        }
        /*TbItemParam tbItemParam = new TbItemParam();
        tbItemParam.setItemCatId(item.getCid());
        tbItemParamMapper.selectOne(tbItemParam);*/
        //根据商品 ID 查询商品规格信息
        TbItemParamItem tbItemParamItem = new TbItemParamItem();
        tbItemParamItem.setItemId(itemId);
        TbItemParamItem paramItem = tbItemParamItemMapper.selectOne(tbItemParamItem);
        if (paramItem != null) {
            map.put("itemParamItem", paramItem.getParamData());
        }
        return map;
    }

    @Override
    public void updateTbItem(ItemVo itemVo) {
        //itemVo
        Date date = new Date();
        itemVo.setUpdated(date);
        itemVo.setStatus((byte) 1);
        tbItemMapper.updateByPrimaryKeySelective(itemVo);

        //desc
        TbItemDesc tbItemDesc = new TbItemDesc();

        tbItemDesc.setUpdated(date);
        tbItemDesc.setItemDesc(itemVo.getDesc());
        tbItemDescMapper.updateByPrimaryKeySelective(tbItemDesc);

        //item_param_item
        TbItemParamItem tbItemParamItem = new TbItemParamItem();

        tbItemParamItem.setCreated(date);
        tbItemParamItem.setParamData(itemVo.getItemParams());
        tbItemParamItemMapper.updateByPrimaryKeySelective(tbItemParamItem);
    }

    @Override
    public TbItem selectItemInfo(Long itemId) {
        return tbItemMapper.selectByPrimaryKey(itemId);
    }

}
