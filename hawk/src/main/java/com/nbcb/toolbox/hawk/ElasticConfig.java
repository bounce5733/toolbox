package com.nbcb.toolbox.hawk;

import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

import java.util.Collections;
import java.util.List;

/**
 * TODO
 *
 * @Author jiangyonghua
 * @Date 2022/10/18 14:22
 * @Version 1.0
 **/
@Configuration
public class ElasticConfig extends AbstractElasticsearchConfiguration {

    @Value("#{'${my.elasticsearch.uris}'.split(',')}")
    private String[] uris;
    @Value("${my.elasticsearch.username}")
    private String username;
    @Value("${my.elasticsearch.password}")
    private String password;

    @Override
    public RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(uris)
                .withBasicAuth(username, password)
                .build();
        return RestClients.create(clientConfiguration).rest();
    }
}
