package com.dxt.gaotie.cloud.tkp.repository;

import com.dxt.gaotie.cloud.tkp.entity.TkpArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2018/10/25/025.
 */
public interface ArticleRepository extends JpaRepository<TkpArticle, Integer>{

    @Query("select a.id, a.title from TkpArticle a where a.catalogId=?1")
    public List<TkpArticle> findByCatalog(Integer catalogId);


}
