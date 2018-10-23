package com.dxt.gaotie.cloud.tkp.neo4j.node;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

/**
 * 铁路局
 * Created by admin on 2018/10/22.
 */
@NodeEntity
public class RailwayBureau extends TkpNode{

    private String bureauCode;

    @Relationship(type = "Have")
    private List<Line> lines;

    @Relationship(type = "Have")
    private List<Station> stations;


}
