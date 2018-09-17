package com.pinyougou.solrutil.config;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.convert.MappingSolrConverter;
import org.springframework.data.solr.core.mapping.SimpleSolrMappingContext;

@Configuration
public class SolrConfig {

    @Value("${spring.data.solr.host}")
    private String solrHost;

    /**
     * 配置SolrTemplate
     */
    @Bean
    public SolrTemplate solrTemplate() {
        HttpSolrClient solrServer= new HttpSolrClient.Builder(solrHost).build();
        SolrTemplate template = new SolrTemplate(solrServer);
        //解决默认动态域前缀不加载的问题
        SimpleSolrMappingContext simpleSolrMappingContext=new SimpleSolrMappingContext();
        MappingSolrConverter mappingSolrConverter=new MappingSolrConverter(simpleSolrMappingContext);
        template.setSolrConverter(mappingSolrConverter);

        return template;
    }

}