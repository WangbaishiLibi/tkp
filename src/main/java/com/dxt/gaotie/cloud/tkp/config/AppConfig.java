package com.dxt.gaotie.cloud.tkp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/7/10/010.
 */

@Component
public class AppConfig {



    @Value("${ffmpeg_path}")
    private String ffmpegPath;

    @Value("${server_domain}")
    private String serverDomain;

    @Value("${super_admin.username}")
    private String superUsername;

    @Value("${super_admin.password}")
    private String superPassword;





    public AppConfig() {

    }


    public String getFfmpegPath() {
        return ffmpegPath;
    }



    public String getSuperUsername() {
        return superUsername;
    }



    public String getSuperPassword() {
        return superPassword;
    }



    public String getServerDomain() {
        return serverDomain;
    }



}
