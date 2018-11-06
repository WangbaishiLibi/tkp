package com.dxt.gaotie.cloud.tkp.controller;/**
 * Created by admin on 2018/11/6.
 */

import com.dxt.gaotie.cloud.tkp.entity.TkpArticle;
import com.dxt.gaotie.cloud.tkp.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ ClassName: ArticleController
 * @ Description: TODO
 * @ author：黎国敏，邵玉祥，丁志翔，吴坤，李碧，陈阳
 * @ date： 2018/11/6 16:33
 * @ version： V1.0.0
 * @ CopyRight：武汉点线通软件有限责任公司
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @GetMapping("/getByCatalog")
    public List<TkpArticle> getByCatalog(Integer catalogId){
        return articleService.getByCatalog(catalogId);
    }





}
