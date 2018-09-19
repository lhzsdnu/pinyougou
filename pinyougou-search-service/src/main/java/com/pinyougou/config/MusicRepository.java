package com.pinyougou.config;

import com.pinyougou.pojo.CopyItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicRepository extends SolrCrudRepository<CopyItem, String> {

    ScoredPage findByKeywordsEquals(String keywords,Pageable pageable);

}