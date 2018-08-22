package com.ing.elasticsearch.imdbdemo.config;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.ing.elasticsearch.imdbdemo.dao")
@ComponentScan(basePackages = { "com.ing.elasticsearch.imdbdemo.service" })
public class ESConfig {

    @Value("${elasticsearch.host}")
    private String EsHost;

    @Value("${elasticsearch.port}")
    private int EsPort;

    @Value("${elasticsearch.clustername}")
    private String EsClusterName;

    @Value("${elasticsearch.home:/home/erolshaban/elasticsearch/6.3.1}")
    private String elasticsearchHome;


    /*@Bean
    public Client client()  {

        Settings esSettings = Settings.builder()
                .put("cluster.name", EsClusterName)
                .build();


        //https://www.elastic.co/guide/en/elasticsearch/guide/current/_transport_client_versus_node_client.html
        return TransportClient
                 .settings(esSettings)
                .build()
                .addTransportAddress(
                        new InetSocketTransportAddress(InetAddress.getByName(EsHost), EsPort));
    }*/


    @Bean
    public Client client() throws Exception {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        Settings elasticsearchSettings = Settings.builder()
                .put("client.transport.sniff", true)
                .put("path.home", elasticsearchHome)
                .put("cluster.name", EsClusterName).build();
        TransportClient client = new PreBuiltTransportClient(elasticsearchSettings);
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

        return client;
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() throws Exception {
        return new ElasticsearchTemplate(client());
    }
    //Embedded Elasticsearch Server
    /*@Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchTemplate(nodeBuilder().local(true).node().client());
    }*/

}