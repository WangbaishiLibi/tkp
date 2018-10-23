package com.dxt.gaotie.cloud.tkp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

/**
 * Created by admin on 2018/10/21.
 */
@SpringBootApplication
@EnableNeo4jRepositories
public class TKPApplication {

    public static void main(String[] args){
        ConfigurableApplicationContext context = SpringApplication.run(TKPApplication.class, args);
        System.out.println("TKP服务已启动！  port:" + context.getEnvironment().getProperty("server.port"));
        System.out.println("-------------低调的分割线٩(๑❛ᴗ❛๑)۶------------");
    }


}
