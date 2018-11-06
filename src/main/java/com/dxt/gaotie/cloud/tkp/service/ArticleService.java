package com.dxt.gaotie.cloud.tkp.service;/**
 * Created by admin on 2018/11/2.
 */

import com.dxt.gaotie.cloud.tkp.entity.TkpArticle;
import com.dxt.gaotie.cloud.tkp.repository.ArticleRepository;
import com.dxt.gaotie.cloud.tkp.repository.VArticleRepository;
import com.dxt.gaotie.cloud.tkp.view.ArticleView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @ ClassName: ArticleService
 * @ Description: TODO
 * @ author：黎国敏，邵玉祥，丁志翔，吴坤，李碧，陈阳
 * @ date： 2018/11/2 20:11
 * @ version： V1.0.0
 * @ CopyRight：武汉点线通软件有限责任公司
 */
@Service
@Transactional
public class ArticleService {

    @Autowired
    VArticleRepository vArticleRepository;

    @Autowired
    ArticleRepository articleRepository;

    public ArticleView articleView(Integer id){
        ArticleView articleView = new ArticleView();
        articleView.article = vArticleRepository.findOne(id);
        return articleView;
    }


    public List<TkpArticle> getByCatalog(Integer catalogId){
        return articleRepository.findByCatalog(catalogId);
    }

}
