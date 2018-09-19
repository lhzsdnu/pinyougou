package com.pinyougou.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.config.MusicRepository;
import com.pinyougou.mapper.ItemMapper;
import com.pinyougou.pojo.CopyItem;
import com.pinyougou.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
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

    private Map searchList(Map searchMap) {
        Map<String, Object> map = new HashMap<>();

        //按照关键字查询
        String str = searchMap.get("keywords").toString();
        Pageable pageable = PageRequest.of(0, 20);
        //添加查询条件
        HighlightPage<CopyItem> page = musicRepository.findByKeywordsLike(str, pageable);
        //循环高亮入口集合
        for (HighlightEntry<CopyItem> h : page.getHighlighted()) {
            //获取原实体类
            CopyItem item = h.getEntity();
            //实际上此处是遍历的，但现只要求title高亮
            if (h.getHighlights().size() > 0 && h.getHighlights().get(0).getSnipplets().size() > 0) {
                //设置高亮的结果
                System.out.println(h.getHighlights().get(0).getSnipplets().get(0));
                item.setTitle(h.getHighlights().get(0).getSnipplets().get(0));
            }
        }
        map.put("rows", page.getContent());
        return map;
    }

    @Override
    public Map<String, Object> search(Map searchMap) {
        Map<String, Object> map = new HashMap<>();
        //putAll可以合并两个Map，只不过如果有相同的key那么用后面的覆盖前面的

        //1.查询列表
        map.putAll(searchList(searchMap));
        return map;
    }
}
