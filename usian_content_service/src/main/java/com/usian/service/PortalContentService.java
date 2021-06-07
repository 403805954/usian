package com.usian.service;

import com.usian.vo.ADContentVo;

import java.util.List;

public interface PortalContentService {
    List<ADContentVo> selectFrontendContentByAD();
}
