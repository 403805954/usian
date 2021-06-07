package com.usian.mapper;

import com.usian.vo.SearchItem;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SearchItemMapper extends Mapper<SearchItem> {

    List<SearchItem> queryAll4ES();

}
