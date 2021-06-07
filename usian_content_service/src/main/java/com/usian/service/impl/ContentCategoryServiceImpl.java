package com.usian.service.impl;

import com.usian.mapper.TbContentCategoryMapper;
import com.usian.pojo.TbContentCategory;
import com.usian.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;

    @Override
    public List<TbContentCategory> selectContentCategoryByParentId(Long id) {
        TbContentCategory tbContentCategory = new TbContentCategory();
        tbContentCategory.setStatus(1);
        tbContentCategory.setParentId(id);
        return tbContentCategoryMapper.select(tbContentCategory);


    }

    @Override
    @Transactional
    public void insertContentCategory(Long parentId, String name) {
        //添加
        TbContentCategory category = new TbContentCategory();
        category.setParentId(parentId);
        category.setCreated(new Date());
        category.setIsParent(false);
        category.setName(name);
        category.setSortOrder(1);
        category.setStatus(1);
        tbContentCategoryMapper.insertSelective(category);
        //修改父类ispart
        //先判断是否为父类  是 不用修改 ispart  否则修改
        TbContentCategory tbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(parentId);
        if (!tbContentCategory.getIsParent()) {
            tbContentCategory.setIsParent(true);
            tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);
        }


    }

    @Override
    @Transactional
    public void deleteContentCategoryById(Long categoryId) {


        //通过 儿子 的id 查找下面还有没有活的儿子  如果有 return
        TbContentCategory tbContentCategory2 = new TbContentCategory();
        tbContentCategory2.setParentId(categoryId);
        tbContentCategory2.setStatus(1);
        List<TbContentCategory> list1 = tbContentCategoryMapper.select(tbContentCategory2);
        if (list1.size() > 0) {
            return;
        }

        //假删除
        tbContentCategoryMapper.updateById(categoryId);
        //获取父id 通过父id 查找 下面 有没有 活着的 儿子  没有就将ispart =》false

        //子对象
        TbContentCategory tbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(categoryId);

        List<TbContentCategory> list = tbContentCategoryMapper.findByParentId(tbContentCategory.getParentId());
        //没有活的儿子就将 父的ispart =》false
        if (list.size() < 1) {
            TbContentCategory tbContentCategory1 = tbContentCategoryMapper.selectByPrimaryKey(tbContentCategory.getParentId());
            tbContentCategory1.setIsParent(false);
            tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory1);
        }

    }

    @Override
    public void updateContentCategory(Long id, String name) {

        TbContentCategory tbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
        tbContentCategory.setName(name);
        tbContentCategory.setUpdated(new Date());
        tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);
    }
}
