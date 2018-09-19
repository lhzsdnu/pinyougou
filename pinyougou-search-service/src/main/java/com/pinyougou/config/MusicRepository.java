package com.pinyougou.config;

import com.pinyougou.pojo.CopyItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.data.solr.repository.Highlight;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicRepository extends SolrCrudRepository<CopyItem, String> {

    ScoredPage findByKeywordsEquals(String keywords, Pageable pageable);

    //设置高亮的域,高亮前缀,高亮后缀
    @Highlight(prefix = "<em style='color:red'>", postfix = "</em>")
    HighlightPage<CopyItem> findByContent(String content, Pageable pageable);

    @Highlight(prefix = "<em style='color:red'>", postfix = "</em>")
    HighlightPage<CopyItem> findByTitle(String title, Pageable pageable);

    @Highlight(prefix = "<em style='color:red'>", postfix = "</em>")
    HighlightPage<CopyItem> findByTitleOrContentLike(String title, String content, Pageable pageable);


}