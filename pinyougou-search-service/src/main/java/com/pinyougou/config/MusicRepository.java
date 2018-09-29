package com.pinyougou.config;

import com.pinyougou.pojo.CopyItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.repository.Facet;
import org.springframework.data.solr.repository.Highlight;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicRepository extends SolrCrudRepository<CopyItem, String> {

    //设置高亮的域,高亮前缀,高亮后缀
    @Highlight(fields = {"item_title"}, prefix = "<em style='color:red'>", postfix = "</em>")
    @Query(value = "item_keywords:*?0*", filters = {"item_category:*?1*", "item_brand:*?2*"})
    HighlightPage<CopyItem> findByKeywords(String keywords, String category, String brand, Pageable pageable);

    @Facet(fields = {"item_category"})
    @Query(value = "item_keywords:*?0*")
    FacetPage<CopyItem> findByKeywords(String keywords, Pageable pageable);

}