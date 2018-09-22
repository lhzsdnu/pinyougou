package com.pinyougou.config;

import com.pinyougou.pojo.CopyItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.repository.Facet;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicRepositoryGroup extends SolrCrudRepository<CopyItem, String> {

    @Facet(fields = {"item_category"})
    FacetPage<CopyItem> findByKeywordsContaining(String keywords, Pageable pageable);

}