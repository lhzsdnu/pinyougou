package com.pinyougou.config;

import com.pinyougou.pojo.CopyItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.repository.Facet;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicRepository extends SolrCrudRepository<CopyItem, String> {

    @Facet(fields = {"item_category"})
    @Query(value = "item_keywords:*?0*")
    FacetPage<CopyItem> findByKeywords(String keywords, Pageable pageable);

    void deleteByGoodsIdIn(List<Long> goodsIdList);

}