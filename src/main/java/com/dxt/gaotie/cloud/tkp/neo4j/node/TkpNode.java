package com.dxt.gaotie.cloud.tkp.neo4j.node;

import org.neo4j.ogm.annotation.GraphId;

/**
 * Created by admin on 2018/10/21.
 */
public abstract class TkpNode {

    @GraphId
    private Long id;

    private String name;




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
}
