package com.dxt.gaotie.cloud.tkp.service;/**
 * Created by admin on 2018/11/2.
 */

import com.dxt.gaotie.cloud.tkp.dao.EntityDao;
import com.dxt.gaotie.cloud.tkp.entity.SearchModel;
import com.dxt.gaotie.cloud.tkp.repository.CatalogRepository;
import com.dxt.gaotie.cloud.tkp.repository.DictionaryRepository;
import com.dxt.gaotie.cloud.tkp.repository.VArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class SearchService {

    @Autowired
    DictionaryRepository dictionaryRepository;

    @Autowired
    CatalogRepository catalogRepository;

    @Autowired
    VArticleRepository articleRepository;

    @Autowired
    EntityDao entityDao;


    /**
     * @Method: search
     * @Description: 关键字检索
     * @Parameters: [key]
     * @Return: java.util.List<com.dxt.gaotie.cloud.tkp.entity.SearchModel>
     * @Author: 黎国敏，邵玉祥，丁志翔，吴坤，李碧，陈阳 
     * @Date: 2018/11/2 16:58
     * @Version: V1.0.0
     * @CopyRight: 武汉点线通软件有限责任公司
     **/
    public List<SearchModel> search(String key){
        List<SearchModel> searchModels = new ArrayList<>();
        if(StringUtils.isEmpty(key))    return searchModels;

        //solr检索


        //数据库检索
        String dbKey = "%" + key + "%";
        String hql = "select d from TkpDictionary d where d.title like '"+dbKey+"' order by d.weight desc, d.updateTime desc";
        searchModels.addAll(entityDao.findList(hql, 0, 20));
        hql = "select c from TkpCatalog c where c.title like '"+dbKey+"' order by c.seq";
        searchModels.addAll(entityDao.findList(hql, 0, 20));
        hql = "select a from VTkpArticle a where a.title like '"+dbKey+"' or a.abstr like '"+dbKey+"' order by a.views desc, a.updateTime desc";
        searchModels.addAll(entityDao.findList(hql, 0, 20));

        return searchModels;
    }


    /**
     * @Method: associateSearch
     * @Description: 关联检索
     * @Parameters: [key]
     * @Return: java.util.List<com.dxt.gaotie.cloud.tkp.entity.SearchModel>
     * @Author: 黎国敏，邵玉祥，丁志翔，吴坤，李碧，陈阳
     * @Date: 2018/11/2 16:58
     * @Version: V1.0.0
     * @CopyRight: 武汉点线通软件有限责任公司
     **/
    public List<SearchModel> associateSearch(String key){
        List<SearchModel> searchModels = new ArrayList<>();
        if(StringUtils.isEmpty(key))    return searchModels;


        return searchModels;
    }



}
