package com.pinyougou.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.config.ChangeToPinYinJP;
import com.pinyougou.config.MusicRepository;
import com.pinyougou.mapper.ItemMapper;
import com.pinyougou.pojo.CopyItem;
import com.pinyougou.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service(
        version = "${demo.service.version}",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}",
        timeout = 3000
)
@Transactional
public class ItemSearchServiceImpl implements ItemSearchService {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private SolrTemplate solrTemplate;

    @Autowired
    private ChangeToPinYinJP changeToPinYinJP;

    @Override
    public Map<String, Object> search(Map searchMap) {
        Map<String, Object> map = new HashMap<>();

        //Pageable pageable = PageRequest.of(0, 20);
        //添加查询条件
        //ScoredPage<CopyItem> page = musicRepository.findByKeywordsEquals(searchMap.get("keywords").toString(),pageable);


        System.out.println("@@@@@@@@"+musicRepository.count());

        Query query=new SimpleQuery();
        //添加查询条件
        Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
        query.addCriteria(criteria);
        ScoredPage<CopyItem> page=solrTemplate.queryForPage("new_core",query,CopyItem.class);

        System.out.println(searchMap.get("keywords")+"======"+page.getContent());

        map.put("rows", page.getContent());

        return map;

    }
}
