package com.dxt.gaotie.cloud.tkp.controller;/**
 * Created by admin on 2018/11/10.
 */

import com.dxt.gaotie.cloud.tkp.neo4j.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ ClassName: TestController
 * @ Description: TODO
 * @ author：黎国敏，邵玉祥，丁志翔，吴坤，李碧，陈阳
 * @ date： 2018/11/10 20:00
 * @ version： V1.0.0
 * @ CopyRight：武汉点线通软件有限责任公司
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    PersonRepository personRepository;

    @GetMapping("/person")
    public Object allPerson(Long id){
        return personRepository.getAll();
    }


}
