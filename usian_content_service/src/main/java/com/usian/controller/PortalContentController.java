package com.usian.controller;

        import com.usian.service.PortalContentService;
        import com.usian.vo.ADContentVo;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RestController;

        import java.util.List;

@RestController
public class PortalContentController {


    @Autowired
    private PortalContentService portalContentService;

    @RequestMapping("/category/selectFrontendContentByAD")
    public List<ADContentVo> selectFrontendContentByAD() {
        return portalContentService.selectFrontendContentByAD();
    }
}
