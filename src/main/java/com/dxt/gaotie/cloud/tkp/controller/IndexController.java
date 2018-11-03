package com.dxt.gaotie.cloud.tkp.controller;/**
 * Created by admin on 2018/11/2.
 */

import com.dxt.gaotie.cloud.tkp.service.ArticleService;
import com.dxt.gaotie.cloud.tkp.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ ClassName: IndexController
 * @ Description: TODO
 * @ author：黎国敏，邵玉祥，丁志翔，吴坤，李碧，陈阳
 * @ date： 2018/11/2 19:47
 * @ version： V1.0.0
 * @ CopyRight：武汉点线通软件有限责任公司
 */
@Controller
public class IndexController {

    @Autowired
    ArticleService articleService;

    @Autowired
    CatalogService catalogService;

    @RequestMapping({"", "/", "/index"})
    public String index(){
        return "index";
    }

    @RequestMapping("/search")
    public String search(String key){
        return "search";
    }

    @RequestMapping("/article/{id}")
    public String article(@PathVariable("id")Integer id, Model model){
        model.addAttribute("view", articleService.articleView(id));
        return "article";
    }


    @RequestMapping("/catalog/{id}")
    public String catalog(@PathVariable("id")Integer id, Model model){
        model.addAttribute(catalogService.catalogView(id));
        return "catalog";
    }





}
