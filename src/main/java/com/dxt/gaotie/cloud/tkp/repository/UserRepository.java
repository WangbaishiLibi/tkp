package com.dxt.gaotie.cloud.tkp.repository;/**
 * Created by admin on 2018/11/10.
 */

import com.dxt.gaotie.cloud.tkp.entity.UUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ ClassName: UserRepository
 * @ Description: TODO
 * @ author：黎国敏，邵玉祥，丁志翔，吴坤，李碧，陈阳
 * @ date： 2018/11/10 21:26
 * @ version： V1.0.0
 * @ CopyRight：武汉点线通软件有限责任公司
 */
public interface UserRepository extends JpaRepository<UUser, Integer>{
    public UUser findByEmpno(String empno);
}
