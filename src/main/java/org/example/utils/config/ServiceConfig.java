package org.example.utils.config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:config.properties")
public interface ServiceConfig extends Config {
    String env();

    @Key("login_url")
    String loginUrl();

    @Key("api_${env}_url")
    String apiUrl();

    @Key("api_base_path")
    String basePath();

    @Key("client_id")
    String clientId();
    @Key("api_${prop}_test_url")
    String apiTestUrl();

}
