package com.dxt.gaotie.cloud.tkp.repository;

import com.dxt.gaotie.cloud.tkp.entity.VTkpArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2018/10/25/025.
 */
public interface VArticleRepository extends JpaRepository<VTkpArticle, Integer>{

    @Query("select a from VTkpArticle a where a.title like ?1 or a.abstr like ?1 order by a.views desc, a.updateTime desc")
    public List<VTkpArticle> findByKey(String key);


}
