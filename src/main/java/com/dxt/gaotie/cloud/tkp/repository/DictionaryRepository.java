package com.dxt.gaotie.cloud.tkp.repository;

import com.dxt.gaotie.cloud.tkp.entity.TkpDictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2018/10/25/025.
 */
public interface DictionaryRepository extends JpaRepository<TkpDictionary, Integer>{

    @Query("select d from TkpDictionary d where d.title like ?1 order by d.weight desc, d.updateTime desc ")
    public List<TkpDictionary> findByTitle(String title);

}
