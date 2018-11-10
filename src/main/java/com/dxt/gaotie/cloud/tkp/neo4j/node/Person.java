package com.dxt.gaotie.cloud.tkp.neo4j.node;/**
 * Created by admin on 2018/11/10.
 */

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

/**
 * @ ClassName: Person
 * @ Description: TODO
 * @ author：黎国敏，邵玉祥，丁志翔，吴坤，李碧，陈阳
 * @ date： 2018/11/10 19:43
 * @ version： V1.0.0
 * @ CopyRight：武汉点线通软件有限责任公司
 */
@NodeEntity(label = "Person")
public class Person {
    @GraphId
    Long id;

    @Property(name = "name")
    String name;

    @Property(name = "born")
    Integer born;

    public Person() {
    }

    public Person(Long id, String name, Integer born) {
        this.id = id;
        this.name = name;
        this.born = born;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBorn() {
        return born;
    }

    public void setBorn(Integer born) {
        this.born = born;
    }
}
