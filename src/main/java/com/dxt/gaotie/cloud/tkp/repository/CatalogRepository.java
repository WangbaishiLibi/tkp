package com.dxt.gaotie.cloud.tkp.repository;

import com.dxt.gaotie.cloud.tkp.entity.TkpCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by admin on 2018/10/30.
 */
public interface CatalogRepository extends JpaRepository<TkpCatalog, Integer>{

    @Query("select c from TkpCatalog c where c.title like ?1 order by c.seq")
    public List<TkpCatalog> findByTitle(String title);

}
