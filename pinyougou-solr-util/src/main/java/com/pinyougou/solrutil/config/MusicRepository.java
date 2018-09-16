package com.pinyougou.solrutil.config;

import com.pinyougou.solrutil.pojo.CopyItem;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicRepository extends SolrCrudRepository<CopyItem, String> {

}