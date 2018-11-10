package com.dxt.gaotie.cloud.tkp.neo4j.repository;

import com.dxt.gaotie.cloud.tkp.neo4j.node.Person;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import java.util.List;

/**
 * Created by admin on 2018/11/10.
 */
public interface PersonRepository extends GraphRepository<Person>{
    @Query("MATCH (n:Person) RETURN n ")
    List<Person> getAll();
}
