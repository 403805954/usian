package com.usian.service;

import com.usian.pojo.TbItemDesc;

public interface ItemDescService {
    TbItemDesc selectItemDescByItemId(Long itemId);
}
