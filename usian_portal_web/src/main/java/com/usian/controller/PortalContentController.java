package com.usian.controller;

import com.usian.feign.ContentFeign;
import com.usian.util.Result;
import com.usian.vo.ADContentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/frontend")
public class PortalContentController {

    @Autowired
    private ContentFeign contentFeign;

    @RequestMapping("/content/selectFrontendContentByAD")
    public Result selectFrontendContentByAD(){
        try {
            List<ADContentVo> list=contentFeign.selectFrontendContentByAD();
            return Result.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("失败");
        }
    }
}
