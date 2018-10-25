package com.dxt.gaotie.cloud.tkp.repository;

import com.dxt.gaotie.cloud.tkp.entity.EduUserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2018/10/25/025.
 */
public interface UserAnswerRepository extends JpaRepository<EduUserAnswer, Integer>{

    @Query("select ua from EduUserAnswer ua where ua.userId=?1")
    public List<EduUserAnswer> findByUser(Integer userId);


}
