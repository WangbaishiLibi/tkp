package com.dxt.gaotie.cloud.tkp.controller;/**
 * Created by admin on 2018/11/3.
 */

import com.dxt.gaotie.cloud.tkp.entity.SearchModel;
import com.dxt.gaotie.cloud.tkp.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ ClassName: SearchController
 * @ Description: TODO
 * @ author：黎国敏，邵玉祥，丁志翔，吴坤，李碧，陈阳
 * @ date： 2018/11/3 13:38
 * @ version： V1.0.0
 * @ CopyRight：武汉点线通软件有限责任公司
 */
@RestController
@RequestMapping("/s")
public class SearchController {

    @Autowired
    SearchService searchService;


    @PostMapping("")
    public List<SearchModel> search(String wd){
        return searchService.search(wd);
    }


    @PostMapping("/a")
    public List<SearchModel> associateSearch(String wd){
        return searchService.associateSearch(wd);
    }
}
