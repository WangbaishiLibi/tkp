package com.dxt.gaotie.cloud.tkp.neo4j.node;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

/**
 * 行车
 * Created by admin on 2018/10/22.
 */
@NodeEntity
public class Driving extends TkpNode{

    @Relationship(type = "")
    private List<Signal> signals;

}
