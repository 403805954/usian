package com.usian.mapper;

import com.usian.pojo.TbContentCategory;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TbContentCategoryMapper extends Mapper<TbContentCategory> {

    @Update("update tb_content_category set status=2 where id= #{parentId}")
    void updateById(Long parentId);

    @Select("select * from tb_content_category where parent_id=#{parentId} and status=1")
    List<TbContentCategory> findByParentId(Long parentId);
}