package com.dxt.gaotie.cloud.tkp.controller;/**
 * Created by admin on 2018/11/3.
 */

import com.dxt.gaotie.cloud.tkp.service.CatalogService;
import com.dxt.gaotie.cloud.tkp.util.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ ClassName: CatalogController
 * @ Description: TODO
 * @ author：黎国敏，邵玉祥，丁志翔，吴坤，李碧，陈阳
 * @ date： 2018/11/3 21:17
 * @ version： V1.0.0
 * @ CopyRight：武汉点线通软件有限责任公司
 */
@RestController
@RequestMapping("/cat")
public class CatalogController {

    @Autowired
    CatalogService catalogService;


    @GetMapping("/catalogTree")
    public List<Node> catalogTree(Integer parentId){
        return catalogService.catalogTree(parentId);
    }

}
